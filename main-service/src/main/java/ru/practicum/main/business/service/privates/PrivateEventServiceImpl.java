package ru.practicum.main.business.service.privates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.business.helper.Checker;
import ru.practicum.main.business.helper.SetterParamsToEventService;
import ru.practicum.main.events.dto.EventToCreateDto;
import ru.practicum.main.events.dto.EventToGetDto;
import ru.practicum.main.events.dto.EventToUpdateByAdminDto;
import ru.practicum.main.events.exceptions.WrongEventDateException;
import ru.practicum.main.events.exceptions.WrongEventStateException;
import ru.practicum.main.events.mapper.EventMapper;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventState;
import ru.practicum.main.events.service.EventService;
import ru.practicum.main.events.service.PrivateEventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventService eventService;

    private final SetterParamsToEventService setterParamsToEventService;

    private final Checker checker;

    @Override
    public List<EventToGetDto> getEventsByUser(Long userId, Integer from, Integer size) {

        checker.userExistChecker(userId);

        return eventService.getEventsByUserId(userId, from, size).stream()
                .map(EventMapper::toGetDto)
                .peek(setterParamsToEventService::setCategoryNameAndInitiatorName)
                .collect(Collectors.toList());
    }

    @Override
    public EventToGetDto updateEventByUser(Long userId, EventToUpdateByAdminDto eventToUpdateByAdminDto) {

        checker.userExistChecker(userId);

        checker.eventExistChecker(eventToUpdateByAdminDto.getEventId());

        Event event = eventService.getEventById(eventToUpdateByAdminDto.getEventId());

        if (!(event.getState() == EventState.REJECTED || event.getState() == EventState.PENDING)) {
            log.error("PrivateApiService.updateEventsByUser: вы можете изменить событие только со статусом ОЖИДАНИЯ " +
                    "или ОТКЛОНЕНО, текущий статус={}", event.getState().toString());
            throw new WrongEventStateException("неправильное состояние события");
        }

        if (eventToUpdateByAdminDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            log.error("PrivateApiService.updateEventsByUser: eдата события не может быть установлена ранее, чем через 2 часа после текущей " +
                    "время, ты date={}, событие date={}", eventToUpdateByAdminDto.getEventDate(), LocalDateTime.now());
            throw new WrongEventDateException("неправильная дата события");
        }

        if (event.getState() == EventState.REJECTED) {
            event.setState(EventState.PENDING);
        }

        eventToUpdateByAdminDto.setCreatedOn(event.getCreatedOn());
        Event eventToUpdate = EventMapper.eventConstructorToUpdateEvent(event, eventToUpdateByAdminDto);
        eventToUpdate = eventService.updateEvent(eventToUpdate);

        return setterParamsToEventService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(eventToUpdate));

    }

    @Override
    public EventToGetDto addNewEvent(Long userId, EventToCreateDto eventToCreateDto) {

        if (eventToCreateDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            log.error("PrivateApiService.addNewEvent: дата события не может быть установлена ранее, чем через 2 часа после текущей " +
                    "время, ты date={}, событие date={}", eventToCreateDto.getEventDate(), LocalDateTime.now());
            throw new WrongEventDateException("неправильная дата события");
        }

        checker.userExistChecker(userId);

        Event event = EventMapper.toEventFromEventToCreateDto(eventToCreateDto);

        event.setInitiator(userId);

        event = eventService.addNewEvent(event);

        return setterParamsToEventService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(event));

    }

    @Override
    public EventToGetDto getEventByIdByOwner(Long userId, Long eventId) {

        checker.userExistChecker(userId);

        checker.eventExistChecker(eventId);

        Event event = eventService.getEventById(eventId);

        checker.ownerEventChecker(userId, event.getInitiator());

        return setterParamsToEventService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(event));
    }

    @Override
    public EventToGetDto cancelEventByOwner(Long userId, Long eventId) {

        checker.userExistChecker(userId);

        checker.eventExistChecker(eventId);

        Event event = eventService.getEventById(eventId);

        checker.ownerEventChecker(userId, event.getInitiator());

        if (event.getState() != EventState.PENDING) {
            log.error("PrivateApiService.cancelEventByOwner: состояние события должно быть ожидающим отмены, ваше состояние={}",
                    event.getState().toString());
            throw new WrongEventStateException("неправильное состояние события");
        }

        event.setState(EventState.CANCELED);

        return setterParamsToEventService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(eventService.updateEvent(event)));

    }
}
