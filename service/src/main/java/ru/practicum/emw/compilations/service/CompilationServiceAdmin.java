package ru.practicum.emw.compilations.service;

import ru.practicum.emw.compilations.dto.CompilationDto;


public interface CompilationServiceAdmin {
    CompilationDto addCompilationByAdmin(CompilationDto compilationToCreateDto);

    void deleteCompilationByAdminById(Long compId);

    void deleteEventFromCompilationByIdByAdmin(Long compId, Long eventId);

    void addEventToCompilationByIdByAdmin(Long compId, Long eventId);

    void pinOrUnpinCompilationByIdByAdmin(Long compId, boolean b);
}
