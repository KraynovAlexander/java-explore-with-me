package ru.practicum.main.categories.service;

import ru.practicum.main.categories.dto.CategoryDto;

public interface AdminCategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategoryById(Long categoryId);
}
