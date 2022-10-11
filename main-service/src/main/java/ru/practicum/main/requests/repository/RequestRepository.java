package ru.practicum.main.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.requests.model.Request;
import ru.practicum.main.requests.model.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> getRequestsByEvent(Long eventId);

    List<Request> getRequestsByStatusAndEvent(RequestStatus requestStatus, Long eventId);

    List<Request> getRequestsByRequester(Long userId);

    Optional<Request> getFirstByRequesterAndEvent(Long userId, Long eventId);

}
