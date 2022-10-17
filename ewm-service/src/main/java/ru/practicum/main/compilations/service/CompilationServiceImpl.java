package ru.practicum.main.compilations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.compilations.exceptions.CompilationNotFoundException;
import ru.practicum.main.compilations.model.Compilation;
import ru.practicum.main.compilations.repository.CompilationRepository;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    @Override
    public Compilation addCompilationByAdmin(Compilation compilation) {
        return compilationRepository.save(compilation);
    }

    @Override
    public void deleteCompilationById(Long compilationId) {
        compilationRepository.deleteById(compilationId);
    }

    @Override
    public Compilation getCompilationById(Long compilationId) {

        return compilationRepository.findById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException("компиляция не существует"));
    }

    @Override
    public Compilation updateCompilation(Compilation compilation) {

        if (!existCompilationById(compilation.getId())) {
            log.error("CompilationService.getCompilationById: компиляция с id={} не найдено", compilation.getId());
            throw new CompilationNotFoundException("компиляция не найдена");
        }

        return compilationRepository.save(compilation);

    }

    @Override
    public List<Compilation> getAllCompilationsByPinnedByPages(boolean pinned, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id").ascending());

        return compilationRepository.findCompilationsByPinned(pinned, pageable).getContent();
    }

    @Override
    public boolean existCompilationById(Long compilationId) {
        return compilationRepository.existsById(compilationId);
    }

}
