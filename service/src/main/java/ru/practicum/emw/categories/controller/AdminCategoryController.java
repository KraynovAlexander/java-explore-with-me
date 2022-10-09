package ru.practicum.emw.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.categories.model.Create;
import ru.practicum.emw.categories.model.Update;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.categories.service.CategoryServiceAdmin;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryServiceAdmin CategoryServiceadmin;


    @PostMapping
    public DtoCategory addNewCategory(@RequestBody @Validated(Create.class) DtoCategory categoryDto) {
        log.info("(Admin)CategoryController.addNewCategory: получен запрос на добавление новой категории {}", categoryDto.getName());
        return CategoryServiceadmin.addCategory(categoryDto);
    }

    @PatchMapping
    public DtoCategory updateCategory(@RequestBody @Validated(Update.class) DtoCategory categoryDto) {
        log.info("(Admin)CategoryController.updateCategory: получен запрос на обновление категории с id={}", categoryDto.getId());
        return CategoryServiceadmin.updateCategory(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    private void deleteCategory(@PathVariable Long categoryId) {
        log.info("(Admin)CategoryController.deleteCategory: получен запрос на удаление категории с id={}", categoryId);
        CategoryServiceadmin.deleteCategoryById(categoryId);
    }



}
