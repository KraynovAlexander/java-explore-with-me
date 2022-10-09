package ru.practicum.emw.categories.service;

import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.categories.model.Category;

import java.util.List;

public interface CategoryService {
    DtoCategory addNewCategory(DtoCategory categoryDto);

    DtoCategory updateCategory(DtoCategory categoryDto);

    void deleteCategoryById(Long categoryId);

    boolean existById(Long categoryId);

    String getCategoryNameById(Long categoryId);

    Category getCategoryById(Long categoryId);

    List<Category> getCategoriesByPages(Integer from, Integer size);
}
