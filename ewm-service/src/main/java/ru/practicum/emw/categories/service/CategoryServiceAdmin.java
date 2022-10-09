package ru.practicum.emw.categories.service;

import ru.practicum.emw.categories.dto.DtoCategory;

public interface CategoryServiceAdmin {
    DtoCategory addCategory(DtoCategory categoryDto);

    DtoCategory updateCategory(DtoCategory categoryDto);

    void deleteCategoryById(Long categoryId);
}
