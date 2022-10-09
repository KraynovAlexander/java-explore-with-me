package ru.practicum.emw.bisnes.service.publics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.categories.mapper.MapperCategory;
import ru.practicum.emw.categories.service.CategoryService;
import ru.practicum.emw.categories.service.CategoryServicePublic;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCategoryServiceImpl implements CategoryServicePublic {

    private final CategoryService categoryService;

    @Override
    public List<DtoCategory> getAllCategoriesByPages(Integer from, Integer size) {
        log.info("PublicCategoryService.getAllCategoriesByPages: отправить запрос в службу категорий");

        return categoryService.getCategoriesByPages(from, size)
                .stream()
                .map(MapperCategory::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoCategory getCategoryById(Long categoryId) {
        return MapperCategory.toDto(categoryService.getCategoryById(categoryId));
    }
}
