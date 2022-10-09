package ru.practicum.emw.categories.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.categories.model.Category;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperCategory {

    public static Category fromDto(DtoCategory categoryDto) {

        Category category = new Category();

        category.setName(categoryDto.getName());
        Optional.ofNullable(categoryDto.getId()).ifPresent(category::setId);

        return category;

    }

    public static DtoCategory toDto(Category category) {

        DtoCategory categoryDto = new DtoCategory();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;

    }

}
