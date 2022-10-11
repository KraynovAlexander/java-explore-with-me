package ru.practicum.main.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.service.PrivateEventService;
import ru.practicum.main.events.dto.EventToCreateDto;
import ru.practicum.main.events.dto.EventToGetDto;
import ru.practicum.main.events.dto.EventToUpdateByAdminDto;

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

    private final PrivateEventService privateEventService;

    @GetMapping
    public List<EventToGetDto> getEventsByUser(@PathVariable Long userId,
                                               @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                               @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("(Private)EventController.getEventsByUser: получен запрос на получение событий пользователем с id={}",
                userId);

        return privateEventService.getEventsByUser(userId, from, size);

    }

    @PatchMapping
    public EventToGetDto updateEventByUser(@PathVariable Long userId,
                                           @RequestBody EventToUpdateByAdminDto eventToUpdateByAdminDto) {

        log.info("(Private)EventController.updateEventsByUser: получен запрос на обновление события с id={}" +
                "by user with id={}", eventToUpdateByAdminDto.getEventId(), userId);

        return privateEventService.updateEventByUser(userId, eventToUpdateByAdminDto);

    }

    @PostMapping
    public EventToGetDto addNewEvent(@PathVariable Long userId,
                                     @Valid @RequestBody EventToCreateDto eventToCreateDto) {

        log.info("(Private)EventController.addNewEvent: получен запрос на добавление нового события {}",
                eventToCreateDto.getTitle());

        return privateEventService.addNewEvent(userId, eventToCreateDto);

    }

    @GetMapping("/{eventId}")
    public EventToGetDto getEventByIdByOwner(@PathVariable Long userId, @PathVariable Long eventId) {

        log.info("(Private)EventController.getEventByIdByOwner: получен запрос на получение события с id={}" +
                " by owner with id={}", eventId, userId);

        return privateEventService.getEventByIdByOwner(userId, eventId);

    }

    @PatchMapping("/{eventId}")
    public EventToGetDto cancelEventByOwner(@PathVariable Long userId, @PathVariable Long eventId) {

        log.info("(Private)EventController.cancelEventByOwner: получен запрос на отмену мероприятия с " +
                "id={} by owner with id ={}", eventId, userId);

        return privateEventService.cancelEventByOwner(userId, eventId);

    }



}
