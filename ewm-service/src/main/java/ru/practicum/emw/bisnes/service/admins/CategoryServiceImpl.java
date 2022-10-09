package ru.practicum.emw.bisnes.service.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.categories.service.CategoryService;
import ru.practicum.emw.categories.service.CategoryServiceAdmin;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryServiceAdmin {

    private final CategoryService categoryService;

    @Override
    public DtoCategory addCategory(DtoCategory categoryDto) {
        return categoryService.addNewCategory(categoryDto);
    }

    @Override
    public DtoCategory updateCategory(DtoCategory categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {

        categoryService.deleteCategoryById(categoryId);

    }
}
