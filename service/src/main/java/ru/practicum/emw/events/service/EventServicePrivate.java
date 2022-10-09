package ru.practicum.emw.events.service;


import ru.practicum.emw.events.dto.CreateDtoEvent;
import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.dto.UpdateAdminDtoEvent;

import java.util.List;

public interface EventServicePrivate {
    List<GetDtoEvent> getEventsByUser(Long userId, Integer from, Integer size);

    GetDtoEvent updateEventByUser(Long userId, UpdateAdminDtoEvent eventToUpdateByAdminDto);

    GetDtoEvent addNewEvent(Long userId, CreateDtoEvent eventToCreateDto);

    GetDtoEvent getEventByIdByOwner(Long userId, Long eventId);

    GetDtoEvent cancelEventByOwner(Long userId, Long eventId);
}
