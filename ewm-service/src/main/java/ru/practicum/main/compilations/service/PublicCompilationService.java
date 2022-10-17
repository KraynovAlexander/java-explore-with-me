package ru.practicum.main.compilations.service;

import ru.practicum.main.compilations.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getCompilationsByPages(boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compilationId);
}
