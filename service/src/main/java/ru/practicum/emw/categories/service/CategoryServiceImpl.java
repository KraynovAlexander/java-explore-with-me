package ru.practicum.emw.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.categories.exceptions.CategoryNotFoundException;
import ru.practicum.emw.categories.exceptions.CategoryStillUseException;
import ru.practicum.emw.categories.mapper.CategoryMapper;
import ru.practicum.emw.categories.model.Category;
import ru.practicum.emw.categories.repository.CategoryRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public DtoCategory addNewCategory(DtoCategory categoryDto) {

        Category category = CategoryMapper.fromDto(categoryDto);

        log.info("CategoryService.addNewCategory: отправьте запрос в базу данных для добавления новой категории {}", category.getName());

        return CategoryMapper.toDto(categoryRepository.save(category));

    }

    @Override
    public DtoCategory updateCategory(DtoCategory categoryDto) {

        if (!existById(categoryDto.getId())) {
            log.error("CategoryService.updateCategory: категория с id={} не существует", categoryDto.getId());
            throw new CategoryNotFoundException("категория не существует");
        }

        Category category = CategoryMapper.fromDto(categoryDto);

        log.info("CategoryService.updateCategory: отправьте запрос в базу данных для обновления категории {}", category.getId());

        return CategoryMapper.toDto(categoryRepository.save(category));

    }

    @Override
    public void deleteCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("категория не существует"));

        if (category.getEvents().size() > 0) {
            log.error("CategoryService.deleteCategoryById: вы не можете удалить категорию, потому что она все еще используется");
            throw new CategoryStillUseException();
        }

        log.info("CategoryService.deleteCategoryById: отправьте запрос в базу данных на удаление категории с id={}", categoryId);

        categoryRepository.deleteById(categoryId);

    }

    @Override
    public boolean existById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @Override
    public String getCategoryNameById(Long categoryId) {

        return categoryRepository.getCategoryNameById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("категория не существует"));

    }

    @Override
    public Category getCategoryById(Long categoryId) {

        log.info("CategoryService.getCategoryById: отправьте запрос в базу данных, чтобы получить категорию с id={}", categoryId);

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("категория не существует"));
    }

    @Override
    public List<Category> getCategoriesByPages(Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id").ascending());

        return categoryRepository.getCategoriesBy(pageable).getContent();
    }
}
