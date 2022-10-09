package ru.practicum.emw.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.events.dto.CreateDtoEvent;
import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.dto.UpdateAdminDtoEvent;
import ru.practicum.emw.events.service.EventServicePrivate;


import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/events")
@Validated
public class PrivateEventController {

    private final EventServicePrivate EventServiceprivate;

    @GetMapping
    public List<GetDtoEvent> getEventsByUser(@PathVariable Long userId,
                                             @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                             @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("(Private)EventController.getEventsByUser: получен запрос на получение событий пользователем с id={}",
                userId);

        return EventServiceprivate.getEventsByUser(userId, from, size);

    }

    @PatchMapping
    public GetDtoEvent updateEventByUser(@PathVariable Long userId,
                                           @RequestBody UpdateAdminDtoEvent eventToUpdateByAdminDto) {

        log.info("(Private)EventController.updateEventsByUser: получен запрос на обновление события с id={}" +
                "пользователем с id={}", eventToUpdateByAdminDto.getEventId(), userId);

        return EventServiceprivate.updateEventByUser(userId, eventToUpdateByAdminDto);

    }

    @PostMapping
    public GetDtoEvent addNewEvent(@PathVariable Long userId,
                                     @Valid @RequestBody CreateDtoEvent eventToCreateDto) {

        log.info("(Private)EventController.addNewEvent: получен запрос на добавление нового события {}",
                eventToCreateDto.getTitle());

        return EventServiceprivate.addNewEvent(userId, eventToCreateDto);

    }

    @GetMapping("/{eventId}")
    public GetDtoEvent getEventByIdByOwner(@PathVariable Long userId, @PathVariable Long eventId) {

        log.info("(Private)EventController.getEventByIdByOwner: получен запрос на получение события с id={}" +
                " владельцем с id={}", eventId, userId);

        return EventServiceprivate.getEventByIdByOwner(userId, eventId);

    }

    @PatchMapping("/{eventId}")
    public GetDtoEvent cancelEventByOwner(@PathVariable Long userId, @PathVariable Long eventId) {

        log.info("(Private)EventController.cancelEventByOwner: получен запрос на отмену мероприятия с " +
                "id={} владельцем с id ={}", eventId, userId);

        return EventServiceprivate.cancelEventByOwner(userId, eventId);

    }



}
