package ru.practicum.main.categories.service;

import ru.practicum.main.categories.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {


    List<CategoryDto> getAllCategoriesByPages(Integer from, Integer size);

    CategoryDto getCategoryById(Long categoryId);
}
