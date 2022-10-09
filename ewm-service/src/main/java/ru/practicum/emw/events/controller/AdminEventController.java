package ru.practicum.emw.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.dto.UpdateAdminDtoEvent;
import ru.practicum.emw.events.service.EventServiceAdmin;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {

    private final EventServiceAdmin EventServiceadmin;

    @GetMapping
    public List<GetDtoEvent> searchEvents(@RequestParam List<Long> users,
                                     @RequestParam List<String> states,
                                     @RequestParam List<Long> categories,
                                     @RequestParam String rangeStart,
                                     @RequestParam String rangeEnd,
                                     @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                     @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("(Admin)EventController.searchEvents: получен запрос на поиск событий администратором с параметрами:\n" +
                        "users={},\n" +
                        "states={},\n" +
                        "categories={},\n" +
                        "rangeStart={},\n" +
                        "rangeEnd={},\n" +
                        "from={},\n" +
                        "size={},\n",
                users, states, categories, rangeStart, rangeEnd, from, size);

        return EventServiceadmin.eventSearchByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);

    }

    @PutMapping("/{eventId}")
    public GetDtoEvent updateEventByAdmin(@RequestBody UpdateAdminDtoEvent eventToUpdateByAdminDto,
                                          @PathVariable Long eventId) {

        log.info("(Admin)EventController.updateEventByAdmin: получен запрос на обновление события от администратора");

        return EventServiceadmin.updateEventByAdmin(eventToUpdateByAdminDto, eventId);

    }

    @PatchMapping("/{eventId}/publish")
    public GetDtoEvent publishEventByAdmin(@PathVariable Long eventId) {

        log.info("(Admin)EventController.publishEventByAdmin: получен запрос на публикацию события администратором");

        return EventServiceadmin.publishEventByAdmin(eventId);

    }

    @PatchMapping("/{eventId}/reject")
    public GetDtoEvent rejectEventByAdmin(@PathVariable Long eventId) {

        log.info("(Admin)EventController.rejectEventByAdmin: получен запрос на отклонение события администратором");

        return EventServiceadmin.rejectEventByAdmin(eventId);

    }

}
