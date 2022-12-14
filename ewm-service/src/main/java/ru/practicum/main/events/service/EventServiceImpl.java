package ru.practicum.main.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.events.exceptions.EventNotFoundException;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventSort;
import ru.practicum.main.events.model.EventState;
import ru.practicum.main.events.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event addNewEvent(Event event) {

        log.info("EventService.addNewEvent: отправьте запрос в базу данных на создание нового события с заголовком={}", event.getTitle());

        event.setState(EventState.PENDING);

        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {

        log.info("EventService.updateEvent: отправьте запрос в базу данных на создание нового события с заголовком={}", event.getTitle());

        return eventRepository.save(event);

    }

    @Override
    public Event getEventById(Long id) {

        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("событие не найдено"));
    }

    @Override
    public void deleteEventById(Long id) {

        if (!existById(id)) {
            log.error("EventService.deleteEventById: событие с id={} не существует", id);
            throw new EventNotFoundException("событие не найдено");
        }

        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> searchEventsByText(String text, List<Long> categoriesId,
                                          boolean paid, LocalDateTime start, LocalDateTime end,
                                          EventSort eventSort, Integer from, Integer size) {

        String fieldToSort = "";

        if (eventSort == EventSort.EVENT_DATE) {
            fieldToSort = "eventDate";
        } else if (eventSort == EventSort.VIEWS) {
            fieldToSort = "views";
        }

        Pageable pageable = PageRequest.of(from / size, size, Sort.by(fieldToSort).descending());

        return eventRepository.searchEventsByTextAndParams(text, categoriesId, paid,
                start, end, pageable)
                .getContent();
    }

    @Override
    public boolean existById(Long id) {
        return eventRepository.existsById(id);
    }

    @Override
    public List<Event> getEventsByParams(List<Long> users, List<EventState> states,
                                         List<Long> categories, LocalDateTime start,
                                         LocalDateTime end, Pageable pageable) {
        return eventRepository.getEventsByInitiatorInAndStateInAndCategoryInAndEventDateBetween(
                users, states, categories, start, end, pageable).getContent();
    }

    @Override
    public List<Event> getEventsByUserId(Long userId, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id").descending());

        return eventRepository.getEventsByInitiator(userId, pageable).getContent();
    }

    @Override
    public Optional<Event> getEventByCategoryId(Long categoryId) {
        return eventRepository.getFirstByCategory(categoryId);
    }
}
