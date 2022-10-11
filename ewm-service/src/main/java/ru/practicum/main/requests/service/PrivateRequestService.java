package ru.practicum.main.requests.service;

import ru.practicum.main.requests.model.Request;

import java.util.List;

public interface PrivateRequestService {
    List<Request> getRequestsByEventIdByOwner(Long userId, Long eventId);

    Request confirmRequestByEventIdByOwner(Long userId, Long eventId, Long reqId);

    Request rejectRequestByEventIdByOwner(Long userId, Long eventId, Long reqId);

    List<Request> getRequestsByUserId(Long userId);

    Request addRequestToEventByUser(Long userId, Long eventId);

    Request cancelRequestByRequestOwner(Long userId, Long requestId);
}
