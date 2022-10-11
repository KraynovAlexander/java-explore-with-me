package ru.practicum.main.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.service.PublicCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryController {

    private final PublicCategoryService publicCategoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                              @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("(Public)CategoryController.getAllCategories: получен запрос на получение всех категорий");

        return publicCategoryService.getAllCategoriesByPages(from, size);

    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable Long categoryId) {

        log.info("(Public)CategoryController.getCategoryById: получен запрос на получение категории с id={}", categoryId);

        return publicCategoryService.getCategoryById(categoryId);

    }

}
