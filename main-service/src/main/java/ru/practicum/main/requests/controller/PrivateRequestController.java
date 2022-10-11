package ru.practicum.main.requests.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.requests.service.PrivateRequestService;
import ru.practicum.main.requests.model.Request;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@Slf4j
@RequiredArgsConstructor
public class PrivateRequestController {

    private final PrivateRequestService privateRequestService;

    @GetMapping("/events/{eventId}/requests")
    public List<Request> getRequestsByEventIdByOwner(@PathVariable Long userId, @PathVariable Long eventId) {

        log.info("(Private)RequestController.getRequestsByEventIdByOwner: получен запрос на получение " +
                "запросы на мероприятие с id={} владельцем с id={}", eventId, userId);

        return privateRequestService.getRequestsByEventIdByOwner(userId, eventId);

    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public Request confirmRequestByEventIdByOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                                  @PathVariable Long reqId) {

        log.info("(Private)RequestController.confirmRequestByEventIdByOwner: получен запрос на подтверждение " +
                "запрос с id={} событие с id={} владельцем с id={}", reqId, eventId, userId);

        return privateRequestService.confirmRequestByEventIdByOwner(userId, eventId, reqId);

    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public Request rejectRequestByEventIdByOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {

        log.info("(Private)RequestController.rejectRequestByEventIdByOwner: получен запрос на отклонение " +
                "запрос с id={} событие с id={} владельцем с id={}", reqId, eventId, userId);

        return privateRequestService.rejectRequestByEventIdByOwner(userId, eventId, reqId);

    }

    @GetMapping("/requests")
    public List<Request> getRequestsByUserId(@PathVariable Long userId) {

        log.info("(Private)RequestController.getRequestsByUserId: получен запрос на получение запросов от пользователя с " +
                "id={}", userId);

        return privateRequestService.getRequestsByUserId(userId);

    }

    @PostMapping("/requests")
    public Request addRequestToEventByUser(@PathVariable Long userId, @RequestParam Long eventId) {

        log.info("(Private)RequestController.addRequestToEventByUser: получен запрос на добавление нового запроса " +
                "на мероприятие с id={} пользователем с id={}", eventId, userId);

        return privateRequestService.addRequestToEventByUser(userId, eventId);

    }

    @PatchMapping("/requests/{requestId}/cancel")
    public Request cancelRequestByRequestOwner(@PathVariable Long userId, @PathVariable Long requestId) {

        log.info("(Private)RequestController.cancelRequestByRequestOwner: получен запрос на отмену запроса " +
                "с id={} владельцем с id={}", requestId, userId);

        return privateRequestService.cancelRequestByRequestOwner(userId, requestId);

    }

}
