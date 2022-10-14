package ru.practicum.main.business.service.publics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.practicum.main.business.helper.DateTimeStringConverter;
import ru.practicum.main.business.helper.SetterParamsToEventService;
import ru.practicum.main.events.client.EventClient;
import ru.practicum.main.events.dto.EventToGetDto;
import ru.practicum.main.events.exceptions.EventNotFoundException;
import ru.practicum.main.events.mapper.EventMapper;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventSort;
import ru.practicum.main.events.model.EventState;
import ru.practicum.main.events.service.EventService;
import ru.practicum.main.events.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {

    private final String serviceApp;

    private final EventService eventService;

    private final SetterParamsToEventService setterParamsToEventService;

    private final EventClient eventClient;

    @Autowired
    public PublicEventServiceImpl(EventService eventService,
                                  SetterParamsToEventService setterParamsToEventService,
                                  EventClient eventClient,
                                  @Value("${service-app.name}") String serviceApp) {
        this.eventService = eventService;
        this.setterParamsToEventService = setterParamsToEventService;
        this.eventClient = eventClient;
        this.serviceApp = serviceApp;
    }

    @Override
    public EventToGetDto getEventForPublicById(Long eventId, HttpServletRequest request) {

        Event event = eventService.getEventById(eventId);

        if (!(event.getState() == EventState.PUBLISHED)) {
            log.error("PublicApiService.getEventById: событие с id={} не опубликовано", eventId);
            throw new EventNotFoundException("событие не опубликовано");
        }

        EventToGetDto eventToGetDto = setterParamsToEventService
                .setCategoryNameAndInitiatorName(EventMapper.toGetDto(event));

        eventClient.saveStat(serviceApp, request.getRequestURI(), request.getRemoteAddr());

        return eventToGetDto;
    }

    @Override
    public List<EventToGetDto> searchEventsByParams(String text, List<Long> categoriesId,
                                                    boolean paid, String rangeStart, String rangeEnd,
                                                    boolean onlyAvailable, EventSort eventSort,
                                                    Integer from, Integer size, HttpServletRequest request) {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusYears(3);

        if (StringUtils.hasText(rangeStart) || StringUtils.hasText(rangeEnd)) {
            start = DateTimeStringConverter.fromFormattedString(rangeStart);
            end = DateTimeStringConverter.fromFormattedString(rangeEnd);
        }

        List<Event> events = eventService.searchEventsByText(text, categoriesId, paid,
                start, end, eventSort, from, size);

        Predicate<Event> onlyAvailablePredicate = event -> event.getConfirmedRequests() < event.getParticipantLimit();

        eventClient.saveStat(serviceApp, request.getRequestURI(), request.getRemoteAddr());

        if (onlyAvailable) {
            return events.stream()
                    .filter(onlyAvailablePredicate)
                    .map(EventMapper::toGetDto)
                    .peek(setterParamsToEventService::setCategoryNameAndInitiatorName)
                    .collect(Collectors.toList());
        } else {
            return events.stream()
                    .map(EventMapper::toGetDto)
                    .peek(setterParamsToEventService::setCategoryNameAndInitiatorName)
                    .collect(Collectors.toList());
        }

    }
}
