package ru.practicum.emw.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.compilations.dto.CompilationDto;
import ru.practicum.emw.compilations.service.CompilationServiceAdmin;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationController {

    private final CompilationServiceAdmin CompilationServiceadmin;

    @PostMapping
    CompilationDto addCompilation(@RequestBody @Valid CompilationDto compilationToCreateDto) {

        log.info("(Admin)CompilationController.addNewCompilation: получен запрос на добавление новой компиляции");

        return CompilationServiceadmin.addCompilationByAdmin(compilationToCreateDto);

    }

    @DeleteMapping("/{compId}")
    void deleteCompilationById(@PathVariable Long compId) {

        log.info("(Admin)CompilationController.deleteCompilationById: получен запрос на удаление компиляции");

        CompilationServiceadmin.deleteCompilationByAdminById(compId);

    }

    @DeleteMapping("/{compId}/events/{eventId}")
    void deleteEventFromCompilationById(@PathVariable Long compId, @PathVariable Long eventId) {

        log.info("(Admin)CompilationController.deleteEventFromCompilationById: получен запрос на удаление события " +
                "из подборки администратора");

        CompilationServiceadmin.deleteEventFromCompilationByIdByAdmin(compId, eventId);

    }

    @PatchMapping("/{compId}/events/{eventId}")
    void addEventToCompilationById(@PathVariable Long compId, @PathVariable Long eventId) {

        log.info("(Admin)CompilationController.addEventToCompilationById: получен запрос на добавление события " +
                "к компиляции администратором");

       CompilationServiceadmin.addEventToCompilationByIdByAdmin(compId, eventId);

    }

    @PatchMapping("/{compId}/pin")
    void pinCompilationById(@PathVariable Long compId) {

        log.info("(Admin)CompilationController.pinCompilationById: получен запрос на закрепление компиляции администратором");

        CompilationServiceadmin.pinOrUnpinCompilationByIdByAdmin(compId, true);

    }

    @DeleteMapping("/{compId}/pin")
    void unpinCompilationById(@PathVariable Long compId) {

        log.info("(Admin)CompilationController.unpinCompilationById: получен запрос на открепление компиляции от администратора");

        CompilationServiceadmin.pinOrUnpinCompilationByIdByAdmin(compId, false);

    }

}
