package ru.practicum.main.categories.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.main.categories.model.Category;

import java.util.Optional;

@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
