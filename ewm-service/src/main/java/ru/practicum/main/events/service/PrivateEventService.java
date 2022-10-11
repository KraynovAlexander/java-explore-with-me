package ru.practicum.main.events.service;

import ru.practicum.main.events.dto.EventToCreateDto;
import ru.practicum.main.events.dto.EventToGetDto;
import ru.practicum.main.events.dto.EventToUpdateByAdminDto;

import java.util.List;

public interface PrivateEventService {
    List<EventToGetDto> getEventsByUser(Long userId, Integer from, Integer size);

    EventToGetDto updateEventByUser(Long userId, EventToUpdateByAdminDto eventToUpdateByAdminDto);

    EventToGetDto addNewEvent(Long userId, EventToCreateDto eventToCreateDto);

    EventToGetDto getEventByIdByOwner(Long userId, Long eventId);

    EventToGetDto cancelEventByOwner(Long userId, Long eventId);
}
