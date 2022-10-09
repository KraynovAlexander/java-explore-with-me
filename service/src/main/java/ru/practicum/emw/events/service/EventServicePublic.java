package ru.practicum.emw.events.service;



import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.model.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventServicePublic {
    GetDtoEvent getEventForPublicById(Long eventId, HttpServletRequest request);

    List<GetDtoEvent> searchEventsByParams(String text, List<Long> categories, boolean paid, String rangeStart, String rangeEnd, boolean onlyAvailable, EventSort eventSort, Integer from, Integer size, HttpServletRequest request);
}
