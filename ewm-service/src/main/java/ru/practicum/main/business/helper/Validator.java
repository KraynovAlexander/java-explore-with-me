package ru.practicum.main.business.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.events.exceptions.EventNotFoundException;
import ru.practicum.main.events.exceptions.NoAccessRightException;
import ru.practicum.main.events.service.EventService;
import ru.practicum.main.requests.exceptions.RequestNotFoundException;
import ru.practicum.main.requests.service.RequestService;
import ru.practicum.main.users.exceptions.UserException;
import ru.practicum.main.users.service.UserService;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class Validator {

    private final UserService userService;

    private final EventService eventService;

    private final RequestService requestService;

    public void userExistValidator(Long userId) {
        if (!userService.existById(userId)) {
            throw new UserException("пользователь не найден");
        }
    }

    public void eventExistValidator(Long eventID) {
        if (!eventService.existById(eventID)) {
            throw new EventNotFoundException("не существует запрос не найден");
        }
    }

    public void ownerEventValidator(Long userId, Long initiatorId) {
        if (!initiatorId.equals(userId)) {
            throw new NoAccessRightException("текущий пользователь не является владельцем события");
        }
    }

    public void requestValidator(Long requestId) {
        if (!requestService.existById(requestId)) {
            throw new RequestNotFoundException("не существует запрос не найден");
        }
    }

}
