package ru.practicum.main.events.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> getEventsByInitiatorInAndStateInAndCategoryInAndEventDateBetween(List<Long> users,
                                                                                 List<EventState> states,
                                                                                 List<Long> categories,
                                                                                 LocalDateTime start,
                                                                                 LocalDateTime end,
                                                                                 Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE (e.annotation LIKE CONCAT('%', ?1, '%') OR e.description LIKE CONCAT('%', ?1, '%')) " +
            "AND e.category IN ?2 AND e.paid = ?3 AND e.eventDate BETWEEN ?4 AND ?5")
    Page<Event> searchEventsByTextAndParams(
            String text, List<Long> categoriesId, boolean paid,
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Event> getEventsByInitiator(Long userId, Pageable pageable);

    Optional<Event> getFirstByCategory(Long categoryId);

}
