package ru.practicum.emw.events.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.events.model.Event;
import ru.practicum.emw.events.model.EventSort;
import ru.practicum.emw.events.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    Event addEvent(Event event);

    Event updateEvent(Event event);

    Event getEventById(Long id);

    void deleteEventById(Long id);

    boolean existById(Long id);

    List<Event> getEventsByUserId(Long userId, Integer from, Integer size);

    List<Event> getEventsByParams(List<Long> users, List<EventState> states, List<Long> categories,
                                  LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> searchEventsByText(String text, List<Long> categoriesId,
                                   boolean paid, LocalDateTime start, LocalDateTime end,
                                   EventSort eventSort, Integer from, Integer size);

    Optional<Event> getEventByCategoryId(Long categoryId);

}
