package ru.practicum.emw.categories.service;

import ru.practicum.emw.categories.dto.DtoCategory;

import java.util.List;

public interface CategoryServicePublic {


    List<DtoCategory> getAllCategoriesByPages(Integer from, Integer size);

    DtoCategory getCategoryById(Long categoryId);
}
