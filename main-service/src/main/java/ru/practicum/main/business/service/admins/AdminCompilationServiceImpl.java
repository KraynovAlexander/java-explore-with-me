package ru.practicum.main.business.service.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.business.helper.SetterParamsToEventService;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.CompilationToCreateDto;
import ru.practicum.main.compilations.exceptions.CompilationNotFoundException;
import ru.practicum.main.compilations.mapper.CompilationMapper;
import ru.practicum.main.compilations.model.Compilation;
import ru.practicum.main.compilations.service.AdminCompilationService;
import ru.practicum.main.compilations.service.CompilationService;
import ru.practicum.main.events.dto.EventToCompilationDto;
import ru.practicum.main.events.exceptions.DuplicateEventException;
import ru.practicum.main.events.exceptions.EventNotFoundException;
import ru.practicum.main.events.mapper.EventMapper;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final EventService eventService;

    private final CompilationService compilationService;

    private final SetterParamsToEventService setterParamsToEventService;

    @Override
    public CompilationDto addCompilationByAdmin(CompilationToCreateDto compilationToCreateDto) {

        List<Long> eventsId = new ArrayList<>();

        if (!(compilationToCreateDto.getEvents() == null)) {
            eventsId = compilationToCreateDto.getEvents();
        }

        for (Long eventId : eventsId) {
            if (!eventService.existById(eventId)) {
                log.error("AdminService.addNewCompilationByAdmin: событие с id={} не существует", eventId);
                throw new EventNotFoundException("событие не найдено");
            }
        }

        Compilation compilation = CompilationMapper.fromCreateDto(compilationToCreateDto);

        compilation.setEvents(eventsId
                .stream()
                .map(eventService::getEventById)
                .collect(Collectors.toList()));

        compilation = compilationService.addCompilationByAdmin(compilation);

        List<EventToCompilationDto> events = compilation.getEvents()
                .stream()
                .map(EventMapper::toCompilationDto)
                .peek(setterParamsToEventService::setCategoryNameAndInitiatorName)
                .collect(Collectors.toList());

        CompilationDto compilationDto = CompilationMapper.toDto(compilation);
        compilationDto.setEvents(events);

        return compilationDto;

    }

    @Override
    public void deleteCompilationByAdminById(Long compId) {

        if (!compilationService.existCompilationById(compId)) {
            log.error("AdminService.deleteCompilationById: компиляция с id={} не найдено", compId);
            throw new CompilationNotFoundException("компиляция не найдена");
        }

        compilationService.deleteCompilationById(compId);

    }

    @Override
    public void deleteEventFromCompilationByIdByAdmin(Long compId, Long eventId) {

        if (!compilationService.existCompilationById(compId)) {
            log.error("AdminService.deleteCompilationByIdByAdmin: компиляция с id={} не найдено", compId);
            throw new CompilationNotFoundException("компиляция не найдена");
        }

        if (!eventService.existById(eventId)) {
            log.error("AdminService.deleteEventFromCompilationByIdAdmin: событие с id={} не найдено", eventId);
            throw new EventNotFoundException("событие не найдено");
        }

        Event event = eventService.getEventById(eventId);

        Compilation compilation = compilationService.getCompilationById(compId);
        compilation.deleteEvent(event);
        compilationService.updateCompilation(compilation);

    }

    @Override
    public void addEventToCompilationByIdByAdmin(Long compId, Long eventId) {

        if (!eventService.existById(eventId)) {
            log.error("AdminService.addEventToCompilationByIdByAdmin: событие с id={} не найдено", eventId);
            throw new EventNotFoundException("событие не найдено");
        }

        if (!compilationService.existCompilationById(compId)) {
            log.error("AdminService.addEventToCompilationByIdByAdmin: компиляция с id={} не найдено", compId);
            throw new CompilationNotFoundException("компиляция не найдена");
        }

        Event event = eventService.getEventById(eventId);

        if (compilationService.getCompilationById(compId).getEvents().contains(event)) {
            log.error("AdminService.addEventToCompilationByIdByAdmin: событие с id={} уже существуют в компиляции с id={}",
                    eventId, compId);
            throw new DuplicateEventException("событие уже существует в этой компиляции");
        }

        Compilation compilation = compilationService.getCompilationById(compId);
        compilation.addEvent(event);
        compilationService.updateCompilation(compilation);

    }

    @Override
    public void pinOrUnpinCompilationByIdByAdmin(Long compId, boolean pinned) {

        if (!compilationService.existCompilationById(compId)) {
            log.error("AdminService.pinCompilationByIdByAdmin: компиляция с id={} не найдено", compId);
            throw new CompilationNotFoundException("компиляция не найдена");
        }

        Compilation compilation = compilationService.getCompilationById(compId);

        compilation.setPinned(pinned);

        compilation = compilationService.updateCompilation(compilation);

    }
}
