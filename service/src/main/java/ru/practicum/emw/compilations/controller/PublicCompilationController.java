package ru.practicum.emw.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.compilations.service.CompilationServicePublic;
import ru.practicum.emw.compilations.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/compilations")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PublicCompilationController {

    private final CompilationServicePublic CompilationServicepublic;

    @GetMapping
    public List<CompilationDto> getCompilationsByPages(
            @RequestParam(required = false, defaultValue = "true") boolean pinned,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
            @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("(Public)CompilationsController.getCompilationsByPages: получен запрос на получение сборников по" +
                "страницы, pinned={}", pinned);

        return CompilationServicepublic.getCompilationsByPages(pinned, from, size);

    }

    @GetMapping("/{compilationId}")
    public CompilationDto getCompilationById(@PathVariable Long compilationId) {

        log.info("(Public)CompilationsController.getCompilationById: получен запрос на получение компиляции по id={}", compilationId);

        return CompilationServicepublic.getCompilationById(compilationId);

    }

}
