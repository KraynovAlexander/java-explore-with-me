package ru.practicum.emw.requests.service;

import ru.practicum.emw.requests.model.Request;

import java.util.List;

public interface RequestServicePrivate {
    List<Request> getRequestsByEventIdByOwner(Long userId, Long eventId);

    Request confirmRequestByEventIdByOwner(Long userId, Long eventId, Long reqId);

    Request rejectRequestByEventIdByOwner(Long userId, Long eventId, Long reqId);

    List<Request> getRequestsByUserId(Long userId);

    Request addNewRequestToEventByUser(Long userId, Long eventId);

    Request cancelRequestByRequestOwner(Long userId, Long requestId);
}
