package ru.practicum.main.compilations.service;

import ru.practicum.main.compilations.model.Compilation;

import java.util.List;

public interface CompilationService {
    Compilation addCompilationByAdmin(Compilation compilation);

    boolean existCompilationById(Long compilationId);

    void deleteCompilationById(Long compilationId);

    Compilation getCompilationById(Long compilationId);

    Compilation updateCompilation(Compilation compilation);

    List<Compilation> getAllCompilationsByPinnedByPages(boolean pinned, Integer from, Integer size);

}
