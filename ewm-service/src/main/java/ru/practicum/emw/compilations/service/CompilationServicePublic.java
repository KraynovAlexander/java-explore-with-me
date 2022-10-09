package ru.practicum.emw.compilations.service;

import ru.practicum.emw.compilations.dto.CompilationDto;

import java.util.List;

public interface CompilationServicePublic {
    List<CompilationDto> getCompilationsByPages(boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compilationId);
}
