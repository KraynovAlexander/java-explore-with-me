package ru.practicum.main.compilations.service;

import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.CompilationToCreateDto;

public interface AdminCompilationService {
    CompilationDto addCompilationByAdmin(CompilationToCreateDto compilationToCreateDto);

    void deleteCompilationByAdminById(Long compId);

    void deleteEventFromCompilationByIdByAdmin(Long compId, Long eventId);

    void addEventToCompilationByIdByAdmin(Long compId, Long eventId);

    void pinOrUnpinCompilationByIdByAdmin(Long compId, boolean b);
}
