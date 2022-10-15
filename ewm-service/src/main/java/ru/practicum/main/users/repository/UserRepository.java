package ru.practicum.main.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.users.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> getUsersByIdIn(List<Long> ids, Pageable pageable);

    @Query("SELECT u.name FROM User u WHERE u.id = ?1")
    Optional<String> findUserNameById(Long userId);

}
