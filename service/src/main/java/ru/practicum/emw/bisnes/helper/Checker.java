package ru.practicum.emw.bisnes.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.events.exceptions.EventNotFoundException;
import ru.practicum.emw.events.exceptions.NoAccessRightException;
import ru.practicum.emw.events.service.EventService;
import ru.practicum.emw.requests.exceptions.RequestNotFoundException;
import ru.practicum.emw.requests.service.RequestService;
import ru.practicum.emw.users.exceptions.UserNotFoundException;
import ru.practicum.emw.users.service.UserService;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class Checker {

    private final UserService userService;

    private final EventService eventService;

    private final RequestService requestService;

    public void userExistChecker(Long userId) {
        if (!userService.existById(userId)) {
            log.error("Проверка: пользователь с id={} не существует", userId);
            throw new UserNotFoundException("пользователь теперь найден");
        }
    }

    public void eventExistChecker(Long eventID) {
        if (!eventService.existById(eventID)) {
            log.error("Проверка: событие с id={} не существует", eventID);
            throw new EventNotFoundException("событие не найдено");
        }
    }

    public void ownerEventChecker(Long userId, Long initiatorId) {
        if (!initiatorId.equals(userId)) {
            log.error("Проверка: нет права доступа");
            throw new NoAccessRightException("текущий пользователь не является владельцем события");
        }
    }

    public void requestChecker(Long requestId) {
        if (!requestService.existById(requestId)) {
            log.error("Проверка: запрос с id={} не существует", requestId);
            throw new RequestNotFoundException("запрос не найден");
        }
    }

}
