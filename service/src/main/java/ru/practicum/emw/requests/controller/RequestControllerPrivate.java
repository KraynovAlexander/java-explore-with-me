package ru.practicum.emw.requests.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.requests.model.Request;
import ru.practicum.emw.requests.service.RequestServicePrivate;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@Slf4j
@RequiredArgsConstructor
public class RequestControllerPrivate {

    private final RequestServicePrivate RequestServiceprivate;

    @GetMapping("/events/{eventId}/requests")
    public List<Request> getRequestsByEventIdByOwner(@PathVariable Long userId, @PathVariable Long eventId) {

        log.info("(Private)RequestController.getRequestsByEventIdByOwner: получен запрос на получение " +
                "запросы на мероприятие с id={} владельцем с id={}", eventId, userId);

        return RequestServiceprivate.getRequestsByEventIdByOwner(userId, eventId);

    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public Request confirmRequestByEventIdByOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                                  @PathVariable Long reqId) {

        log.info("(Private)RequestController.confirmRequestByEventIdByOwner: получен запрос на подтверждение " +
                "запрос с id={} событие с id={} владельцем с id={}", reqId, eventId, userId);

        return RequestServiceprivate.confirmRequestByEventIdByOwner(userId, eventId, reqId);

    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public Request rejectRequestByEventIdByOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {

        log.info("(Private)RequestController.rejectRequestByEventIdByOwner: получен запрос на отклонение " +
                "запрос с id={} событие с id={} владельцем с id={}", reqId, eventId, userId);

        return RequestServiceprivate.rejectRequestByEventIdByOwner(userId, eventId, reqId);

    }

    @GetMapping("/requests")
    public List<Request> getRequestsByUserId(@PathVariable Long userId) {

        log.info("(Private)RequestController.getRequestsByUserId: получен запрос на получение запросов от пользователя с " +
                "id={}", userId);

        return RequestServiceprivate.getRequestsByUserId(userId);

    }

    @PostMapping("/requests")
    public Request addNewRequestToEventByUser(@PathVariable Long userId, @RequestParam Long eventId) {

        log.info("(Private)RequestController.addNewRequestToEventByUser: получен запрос на добавление нового запроса " +
                "на мероприятие с id={} пользователем с id={}", eventId, userId);

        return RequestServiceprivate.addNewRequestToEventByUser(userId, eventId);

    }

    @PatchMapping("/requests/{requestId}/cancel")
    public Request cancelRequestByRequestOwner(@PathVariable Long userId, @PathVariable Long requestId) {

        log.info("(Private)RequestController.cancelRequestByRequestOwner: получен запрос на отмену запроса " +
                "с id={} владельцем с id={}", requestId, userId);

        return RequestServiceprivate.cancelRequestByRequestOwner(userId, requestId);

    }

}
