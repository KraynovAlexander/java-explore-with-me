package ru.practicum.emw.bisnes.service.privates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.emw.bisnes.helper.Checker;
import ru.practicum.emw.bisnes.helper.SetterParamsService;
import ru.practicum.emw.events.dto.CreateDtoEvent;
import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.dto.UpdateAdminDtoEvent;
import ru.practicum.emw.events.exceptions.EventDateException;
import ru.practicum.emw.events.exceptions.EventStateException;
import ru.practicum.emw.events.mapper.EventMapper;
import ru.practicum.emw.events.model.Event;
import ru.practicum.emw.events.model.EventState;
import ru.practicum.emw.events.service.EventService;
import ru.practicum.emw.events.service.EventServicePrivate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements EventServicePrivate {

    private final EventService eventService;

    private final SetterParamsService setterParamsService;

    private final Checker checker;

    @Override
    public List<GetDtoEvent> getEventsByUser(Long userId, Integer from, Integer size) {

        checker.userExistChecker(userId);

        return eventService.getEventsByUserId(userId, from, size).stream()
                .map(EventMapper::toGetDto)
                .peek(setterParamsService::setCategoryNameAndInitiatorName)
                .collect(Collectors.toList());
    }

    @Override
    public GetDtoEvent updateEventByUser(Long userId, UpdateAdminDtoEvent eventToUpdateByAdminDto) {

        checker.userExistChecker(userId);

        checker.eventExistChecker(eventToUpdateByAdminDto.getEventId());

        Event event = eventService.getEventById(eventToUpdateByAdminDto.getEventId());

        if (!(event.getState() == EventState.REJECTED || event.getState() == EventState.PENDING)) {
            log.error("PrivateApiService.updateEventsByUser: вы можете изменить событие только со статусом ОЖИДАНИЯ " +
                    "или ОТКЛОНЕНО, текущий статус={}", event.getState().toString());
            throw new EventStateException("неправильное состояние события");
        }

        if (eventToUpdateByAdminDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            log.error("PrivateApiService.updateEventsByUser: дата события не может быть установлена ранее, чем через 2 часа после текущей " +
                    "время, ты встречаешься={}, дата события={}", eventToUpdateByAdminDto.getEventDate(), LocalDateTime.now());
            throw new EventDateException("неправильная дата события");
        }

        if (event.getState() == EventState.REJECTED) {
            event.setState(EventState.PENDING);
        }

        eventToUpdateByAdminDto.setCreatedOn(event.getCreatedOn());
        Event eventToUpdate = EventMapper.eventConstructorToUpdateEvent(event, eventToUpdateByAdminDto);
        eventToUpdate = eventService.updateEvent(eventToUpdate);

        return setterParamsService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(eventToUpdate));

    }

    @Override
    public GetDtoEvent addNewEvent(Long userId, CreateDtoEvent eventToCreateDto) {

        if (eventToCreateDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            log.error("PrivateApiService.addNewEvent: дата события не может быть установлена ранее, чем через 2 часа после текущей " +
                    "время, ты встречаешься={}, дата события={}", eventToCreateDto.getEventDate(), LocalDateTime.now());
            throw new EventDateException("wнеправильная дата события");
        }

        checker.userExistChecker(userId);

        Event event = EventMapper.toEventFromEventToCreateDto(eventToCreateDto);

        event.setInitiator(userId);

        event = eventService.addEvent(event);

        return setterParamsService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(event));

    }

    @Override
    public GetDtoEvent getEventByIdByOwner(Long userId, Long eventId) {

        checker.userExistChecker(userId);

        checker.eventExistChecker(eventId);

        Event event = eventService.getEventById(eventId);

        checker.ownerEventChecker(userId, event.getInitiator());

        return setterParamsService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(event));
    }

    @Override
    public GetDtoEvent cancelEventByOwner(Long userId, Long eventId) {

        checker.userExistChecker(userId);

        checker.eventExistChecker(eventId);

        Event event = eventService.getEventById(eventId);

        checker.ownerEventChecker(userId, event.getInitiator());

        if (event.getState() != EventState.PENDING) {
            log.error("PrivateApiService.cancelEventByOwner: состояние события должно быть ожидающим отмены, ваше состояние={}",
                    event.getState().toString());
            throw new EventStateException("неправильное состояние события");
        }

        event.setState(EventState.CANCELED);

        return setterParamsService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(eventService.updateEvent(event)));

    }
}
