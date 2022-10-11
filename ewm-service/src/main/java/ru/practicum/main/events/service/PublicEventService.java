package ru.practicum.main.events.service;

import ru.practicum.main.events.dto.EventToGetDto;
import ru.practicum.main.events.model.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventService {
    EventToGetDto getEventForPublicById(Long eventId, HttpServletRequest request);

    List<EventToGetDto> searchEventsByParams(String text, List<Long> categories, boolean paid, String rangeStart, String rangeEnd, boolean onlyAvailable, EventSort eventSort, Integer from, Integer size, HttpServletRequest request);
}
