package ru.practicum.main.categories.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.categories.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> getCategoriesBy(Pageable pageable);


    @Query("SELECT c.name FROM Category c WHERE c.id = ?1")
    Optional<String> getCategoryNameById(Long categoryId);

}
