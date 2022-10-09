package ru.practicum.emw.bisnes.service.publics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.bisnes.helper.SetterParamsService;
import ru.practicum.emw.compilations.dto.CompilationDto;
import ru.practicum.emw.compilations.exceptions.NotFoundExceptionCompilation;
import ru.practicum.emw.compilations.mapper.MapperCompilation;
import ru.practicum.emw.compilations.model.Compilation;
import ru.practicum.emw.compilations.service.CompilationService;
import ru.practicum.emw.compilations.service.CompilationServicePublic;
import ru.practicum.emw.events.dto.CompilationDtoEvent;
import ru.practicum.emw.events.mapper.EventMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCompilationServiceImpl implements CompilationServicePublic {

    private final CompilationService compilationService;

    private final SetterParamsService setterParamsService;

    @Override
    public List<CompilationDto> getCompilationsByPages(boolean pinned, Integer from, Integer size) {

        List<Compilation> compilations = compilationService.getAllCompilationsByPinnedByPages(pinned, from, size);

        List<CompilationDto> compilationDtos = new ArrayList<>();

        for (Compilation compilation : compilations) {

            CompilationDto compilationDto = MapperCompilation.toDto(compilation);

            compilationDto.setEvents(compilation.getEvents()
                    .stream()
                    .map(EventMapper::toCompilationDto)
                    .peek(setterParamsService::setCategoryNameAndInitiatorName)
                    .collect(Collectors.toList()));

            compilationDtos.add(compilationDto);

        }

        return compilationDtos;

    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) {

        if (!compilationService.existCompilationById(compilationId)) {
            log.error("PublicApiService.getCompilationById: компиляция с id={} не существует", compilationId);
            throw new NotFoundExceptionCompilation("компиляция не найдена");
        }

        Compilation compilation = compilationService.getCompilationById(compilationId);

        List<CompilationDtoEvent> events = compilation.getEvents()
                .stream()
                .map(EventMapper::toCompilationDto)
                .peek(setterParamsService::setCategoryNameAndInitiatorName)
                .collect(Collectors.toList());

        CompilationDto compilationDto = MapperCompilation.toDto(compilation);
        compilationDto.setEvents(events);

        return compilationDto;
    }
}
