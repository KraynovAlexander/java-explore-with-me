package ru.practicum.emw.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.categories.service.CategoryServicePublic;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryController {

    private final CategoryServicePublic CategoryServicepublic;

    @GetMapping
    public List<DtoCategory> getAllCategories(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                              @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("(Public)CategoryController.getAllCategories: получен запрос на получение всех категорий");

        return CategoryServicepublic.getAllCategoriesByPages(from, size);

    }

    @GetMapping("/{categoryId}")
    public DtoCategory getCategoryById(@PathVariable Long categoryId) {

        log.info("(Public)CategoryController.getCategoryById: получен запрос на получение категории с id={}", categoryId);

        return CategoryServicepublic.getCategoryById(categoryId);

    }

}
