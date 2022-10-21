package ru.practicum.main.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.categories.model.Create;
import ru.practicum.main.categories.model.Update;
import ru.practicum.main.categories.service.AdminCategoryService;
import ru.practicum.main.categories.dto.CategoryDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;


    @PostMapping
    public CategoryDto addNewCategory(@RequestBody @Validated(Create.class) CategoryDto categoryDto) {
        log.info("(Admin)CategoryController.addNewCategory: получен запрос на добавление новой категории {}", categoryDto.getName());
        return adminCategoryService.addCategory(categoryDto);
    }

    @PutMapping
    public CategoryDto updateCategory(@RequestBody @Validated(Update.class) CategoryDto categoryDto) {
        log.info("(Admin)CategoryController.updateCategory: получен запрос на обновление категории с id={}", categoryDto.getId());
        return adminCategoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    private void deleteCategory(@PathVariable Long categoryId) {
        log.info("(Admin)CategoryController.deleteCategory: получен запрос на удаление категории с id={}", categoryId);
        adminCategoryService.deleteCategoryById(categoryId);
    }



}
