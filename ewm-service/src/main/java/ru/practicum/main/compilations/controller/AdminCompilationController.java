package ru.practicum.main.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilations.service.AdminCompilationService;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.CompilationToCreateDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    CompilationDto addCompilation(@RequestBody @Valid CompilationToCreateDto compilationToCreateDto) {

        log.info("(Admin)CompilationController.addCompilation: получен запрос на добавление новой компиляции");

        return adminCompilationService.addCompilationByAdmin(compilationToCreateDto);

    }

    @DeleteMapping("/{compId}")
    void deleteCompilationById(@PathVariable Long compId) {

        log.info("(Admin)CompilationController.deleteCompilationById: получен запрос на удаление компиляции");

        adminCompilationService.deleteCompilationByAdminById(compId);

    }

    @DeleteMapping("/{compId}/events/{eventId}")
    void deleteEventFromCompilationById(@PathVariable Long compId, @PathVariable Long eventId) {

        log.info("(Admin)CompilationController.deleteEventFromCompilationById: получен запрос на удаление события " +
                "из подборки администратора");

        adminCompilationService.deleteEventFromCompilationByIdByAdmin(compId, eventId);

    }

    @PatchMapping("/{compId}/events/{eventId}")
    void addEventToCompilationById(@PathVariable Long compId, @PathVariable Long eventId) {

        log.info("(Admin)CompilationController.addEventToCompilationById: получен запрос на добавление события " +
                "к компиляции администратором");

        adminCompilationService.addEventToCompilationByIdByAdmin(compId, eventId);

    }

    @PatchMapping("/{compId}/pin")
    void pinCompilationById(@PathVariable Long compId) {

        log.info("(Admin)CompilationController.pinCompilationById: получен запрос на закрепление компиляции администратором");

        adminCompilationService.pinOrUnpinCompilationByIdByAdmin(compId, true);

    }

    @DeleteMapping("/{compId}/pin")
    void unpinCompilationById(@PathVariable Long compId) {

        log.info("(Admin)CompilationController.unpinCompilationById: получен запрос на открепление компиляции от администратора");

        adminCompilationService.pinOrUnpinCompilationByIdByAdmin(compId, false);

    }

}
