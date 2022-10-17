package ru.practicum.main.business.service.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.service.AdminCategoryService;
import ru.practicum.main.categories.service.CategoryService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryService categoryService;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {

        categoryService.deleteCategoryById(categoryId);

    }
}
