package ru.practicum.main.categories.mapper;


import lombok.experimental.UtilityClass;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.model.Category;

import java.util.Optional;

@UtilityClass
public class CategoryMapper {

    public static Category fromDto(CategoryDto categoryDto) {

        Category category = new Category();

        category.setName(categoryDto.getName());
        Optional.ofNullable(categoryDto.getId()).ifPresent(category::setId);

        return category;

    }

    public static CategoryDto toDto(Category category) {

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;

    }

}
