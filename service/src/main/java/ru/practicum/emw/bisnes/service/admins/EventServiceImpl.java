package ru.practicum.emw.bisnes.service.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.emw.bisnes.helper.DateTimeConverter;
import ru.practicum.emw.bisnes.helper.SetterParamsService;
import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.dto.UpdateAdminDtoEvent;
import ru.practicum.emw.events.exceptions.EventStateException;
import ru.practicum.emw.events.mapper.EventMapper;
import ru.practicum.emw.events.model.Event;
import ru.practicum.emw.events.model.EventState;
import ru.practicum.emw.events.service.EventService;
import ru.practicum.emw.events.service.EventServiceAdmin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventServiceAdmin {

    private final EventService eventService;

    private final SetterParamsService setterParamsToEventService;

    @Override
    public List<GetDtoEvent> eventSearchByAdmin(List<Long> users, List<String> states,
                                                List<Long> categories, String rangeStart,
                                                String rangeEnd, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id").descending());

        LocalDateTime start = DateTimeConverter.fromFormattedString(rangeStart);
        LocalDateTime end = DateTimeConverter.fromFormattedString(rangeEnd);

        List<EventState> eventStates = states.stream()
                .map(EventState::from)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<Event> events = eventService.getEventsByParams(users, eventStates, categories, start, end, pageable);

        return events.stream()
                .map(EventMapper::toGetDto)
                .peek(setterParamsToEventService::setCategoryNameAndInitiatorName)
                .collect(Collectors.toList());

    }

    @Override
    public GetDtoEvent updateEventByAdmin(UpdateAdminDtoEvent eventToUpdateByAdminDto, Long eventId) {

        Event event = eventService.getEventById(eventId);

        Event eventToUpdate = EventMapper.eventConstructorToUpdateEvent(event, eventToUpdateByAdminDto);

        eventToUpdate = eventService.updateEvent(eventToUpdate);

        GetDtoEvent eventToGetDto = EventMapper.toGetDto(eventToUpdate);

        return setterParamsToEventService.setCategoryNameAndInitiatorName(eventToGetDto);

    }

    @Override
    public GetDtoEvent publishEventByAdmin(Long eventId) {

        Event event = eventService.getEventById(eventId);

        if (event.getState() != EventState.PENDING) {
            log.error("AdminService.publishEventByAdmin: состояние события должно быть ОЖИДАЮЩИМ, а не {}",event.getState().toString());
            throw new EventStateException("статус для изменения должен быть ОЖИДАЮЩИМ");
        }

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            log.error("AdminService.publishEventByAdmin: \n" +
                    "начало мероприятия должно быть не ранее, чем после даты публикации");
            throw new EventStateException("уже слишком поздно");
        }

        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());

        event = eventService.updateEvent(event);

        return setterParamsToEventService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(event));

    }

    @Override
    public GetDtoEvent rejectEventByAdmin(Long eventId) {

        Event event = eventService.getEventById(eventId);

        if (event.getState() != EventState.PENDING) {
            log.error("AdminService.rejectEventByAdmin: состояние события должно быть ОЖИДАЮЩИМ, а не {}",event.getState().toString());
            throw new EventStateException("статус для изменения должен быть ОЖИДАЮЩИМ");
        }

        event.setState(EventState.CANCELED);

        return setterParamsToEventService.setCategoryNameAndInitiatorName(EventMapper.toGetDto(event));

    }

}
