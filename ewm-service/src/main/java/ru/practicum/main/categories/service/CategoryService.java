package ru.practicum.main.categories.service;

import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.model.Category;

import java.util.Optional;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategoryById(Long categoryId);

    boolean existById(Long categoryId);

    Category getCategoryNameById(Long categoryId);

    Category getCategoryById(Long categoryId);

    Optional<Category> getCategoriesByPages(Integer from, Integer size);
}
