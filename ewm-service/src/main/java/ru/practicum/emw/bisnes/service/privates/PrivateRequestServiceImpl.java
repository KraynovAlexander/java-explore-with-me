package ru.practicum.emw.bisnes.service.privates;

import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.emw.bisnes.helper.Checker;
import ru.practicum.emw.events.exceptions.EventStateException;
import ru.practicum.emw.events.exceptions.NoAccessRightException;
import ru.practicum.emw.events.model.Event;
import ru.practicum.emw.events.model.EventState;
import ru.practicum.emw.events.service.EventService;
import ru.practicum.emw.requests.exceptions.ParticipantsException;
import ru.practicum.emw.requests.exceptions.RequestException;
import ru.practicum.emw.requests.model.Request;
import ru.practicum.emw.requests.model.RequestStatus;
import ru.practicum.emw.requests.service.RequestService;
import ru.practicum.emw.requests.service.RequestServicePrivate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements RequestServicePrivate {

    private final EventService eventService;

    private final RequestService requestService;

    private final Checker checker;


    @Override
    public List<Request> getRequestsByEventIdByOwner(Long userId, Long eventId) {

        checker.userExistChecker(userId);
        checker.eventExistChecker(eventId);

        Event event = eventService.getEventById(eventId);

        checker.ownerEventChecker(userId, event.getInitiator());

        return requestService.getRequestsByEventId(eventId);
    }

    @Override
    public Request confirmRequestByEventIdByOwner(Long userId, Long eventId, Long reqId) {

        checker.userExistChecker(userId);
        checker.eventExistChecker(eventId);

        Event event = eventService.getEventById(eventId);

        checker.ownerEventChecker(userId, event.getInitiator());

        checker.requestChecker(reqId);

        Request request = requestService.getRequestById(reqId);

        if (request.getStatus() != RequestStatus.PENDING) {
            log.error("PrivateApiService.confirmRequestByEventIdByOwner: статус запроса для подтверждения должен быть ОЖИДАЮЩИМ," +
                    " нет {}", request.getStatus().toString());
            throw new RequestException("неправильный статус запроса");
        }

        request.setStatus(RequestStatus.CONFIRMED);

        request = requestService.updateRequest(request);

        event.setConfirmedRequests(event.getConfirmedRequests() + 1);

        event = eventService.updateEvent(event);

        if (event.getConfirmedRequests().equals(event.getParticipantLimit())) {
            List<Request> requestsToReject = requestService.getRequestsWithPendingStatus(eventId)
                    .stream()
                    .peek(x -> x.setStatus(RequestStatus.REJECTED))
                    .collect(Collectors.toList());

            for (Request requestToReject : requestsToReject) {
                requestService.updateRequest(requestToReject);
            }
        }

        return request;

    }

    @Override
    public Request rejectRequestByEventIdByOwner(Long userId, Long eventId, Long reqId) {

        checker.userExistChecker(userId);
        checker.eventExistChecker(eventId);

        Event event = eventService.getEventById(eventId);

        checker.ownerEventChecker(userId, event.getInitiator());

        checker.requestChecker(reqId);

        Request request = requestService.getRequestById(reqId);

        if (request.getStatus() != RequestStatus.PENDING) {
            log.error("PrivateApiService.rejectRequestByEventIdByOwner: статус запроса для отклонения должен быть ОЖИДАЮЩИМ," +
                    " нет {}", request.getStatus().toString());
            throw new RequestException("неправильный статус запроса");
        }

        request.setStatus(RequestStatus.REJECTED);

        return requestService.updateRequest(request);
    }

    @Override
    public List<Request> getRequestsByUserId(Long userId) {

        checker.userExistChecker(userId);

        return requestService.getRequestsByUserId(userId);
    }

    @Override
    public Request addNewRequestToEventByUser(Long userId, Long eventId) {

        checker.userExistChecker(userId);
        checker.eventExistChecker(eventId);

        Optional<Request> checkingRequest = requestService.getRequestByUserIdAndEventId(userId, eventId);

        if (checkingRequest.isPresent()) {
            log.error("PrivateApiService.addRequestToEventByUser: вы не можете выполнить дублирующий запрос, ваш " +
                    "последний запрос id={}", checkingRequest.get().getId());
            throw new DuplicateRequestException("дублирующий запрос");
        }

        Event event = eventService.getEventById(eventId);

        if (event.getInitiator().equals(userId)) {
            log.error("PrivateApiService.addRequestToEventByUser: ты не можешь создать запрос на свое мероприятие, сука");
            throw new DuplicateRequestException("запрос на ваше мероприятие");
        }

        if (event.getState() != EventState.PUBLISHED) {
            log.error("PrivateApiService.addNewRequestToEventByUser: событие не опубликовано, статус события теперь " +
                    "{}", event.getState().toString());
            throw new EventStateException("состояние события не опубликовано");
        }

        if (event.getConfirmedRequests().equals(event.getParticipantLimit()) && event.getParticipantLimit() != 0) {
            log.error("PrivateApiService.addRequestToEventByUser: участники мероприятия полны");
            throw new ParticipantsException();
        }

        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setEvent(eventId);
        request.setRequester(userId);
        request.setStatus(RequestStatus.PENDING);

        if (!event.isRequestModeration() || event.getParticipantLimit().equals(Long.getLong("0"))) {
            request.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            event = eventService.updateEvent(event);

            return requestService.addNewRequest(request);
        }

        return requestService.addNewRequest(request);
    }

    @Override
    public Request cancelRequestByRequestOwner(Long userId, Long requestId) {

        checker.userExistChecker(userId);
        checker.requestChecker(requestId);

        Request request = requestService.getRequestById(requestId);

        if (!request.getRequester().equals(userId)) {
            log.error("PrivateApiService.cancelRequestByRequestOwner: пользователь с id={} не является запросом владельца с" +
                    " id={}", userId, requestId);
            throw new NoAccessRightException("нет права доступа для запроса");
        }

        request.setStatus(RequestStatus.CANCELED);

        return requestService.updateRequest(request);
    }
}
