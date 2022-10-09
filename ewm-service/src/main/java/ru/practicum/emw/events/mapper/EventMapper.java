package ru.practicum.emw.events.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.events.dto.CompilationDtoEvent;
import ru.practicum.emw.events.dto.CreateDtoEvent;
import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.dto.UpdateAdminDtoEvent;
import ru.practicum.emw.events.model.Event;
import ru.practicum.emw.events.model.LocationEvent;
import ru.practicum.emw.users.dto.UserInitiatorDto;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static Event toEventFromEventToCreateDto(CreateDtoEvent eventToCreateDto) {

        Event event = new Event();

        event.setAnnotation(eventToCreateDto.getAnnotation());
        event.setCategory(eventToCreateDto.getCategory());
        event.setDescription(eventToCreateDto.getDescription());
        event.setCreatedOn(eventToCreateDto.getCreatedOn());
        event.setEventDate(eventToCreateDto.getEventDate());
        event.setLat(eventToCreateDto.getLocation().getLat());
        event.setLon(eventToCreateDto.getLocation().getLon());
        event.setPaid(eventToCreateDto.isPaid());
        event.setParticipantLimit(eventToCreateDto.getParticipantLimit());
        event.setRequestModeration(eventToCreateDto.isRequestModeration());
        event.setTitle(eventToCreateDto.getTitle());

        return event;

    }

    public static CompilationDtoEvent toCompilationDto(Event event) {

        CompilationDtoEvent eventToCompilationDto = new CompilationDtoEvent();

        eventToCompilationDto.setId(event.getId());
        eventToCompilationDto.setAnnotation(event.getAnnotation());
        eventToCompilationDto.setCategory(new DtoCategory(event.getCategory(), null));
        eventToCompilationDto.setConfirmedRequests(event.getConfirmedRequests());
        eventToCompilationDto.setEventDate(event.getEventDate());
        eventToCompilationDto.setInitiator(new UserInitiatorDto(event.getInitiator()));
        eventToCompilationDto.setPaid(event.isPaid());
        eventToCompilationDto.setTitle(event.getTitle());

        return eventToCompilationDto;

    }

    public static GetDtoEvent toGetDto(Event event) {

        GetDtoEvent eventToGetDto = new GetDtoEvent();

        eventToGetDto.setId(event.getId());
        eventToGetDto.setAnnotation(event.getAnnotation());
        eventToGetDto.setCategory(new DtoCategory(event.getCategory(), null));
        Optional.ofNullable(event.getConfirmedRequests()).ifPresent(eventToGetDto::setConfirmedRequests);
        eventToGetDto.setCreatedOn(event.getCreatedOn());
        eventToGetDto.setDescription(event.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        eventToGetDto.setEventDate(event.getEventDate().format(formatter));

        eventToGetDto.setInitiator(new UserInitiatorDto(event.getInitiator()));
        eventToGetDto.setLocation(new LocationEvent(event.getLat(), event.getLon()));
        eventToGetDto.setPaid(event.isPaid());
        eventToGetDto.setParticipantLimit(event.getParticipantLimit());
        Optional.ofNullable(event.getPublishedOn()).ifPresent(eventToGetDto::setPublishedOn);
        eventToGetDto.setRequestModeration(event.isRequestModeration());
        eventToGetDto.setTitle(event.getTitle());
        eventToGetDto.setState(event.getState().toString());

        return eventToGetDto;

    }

    public static Event eventConstructorToUpdateEvent(Event event, UpdateAdminDtoEvent UpdateByAdminDtoevent) {

        Event eventToUpdate = new Event();

        eventToUpdate.setId(UpdateByAdminDtoevent.getEventId());

        if (!(UpdateByAdminDtoevent.getAnnotation() == null)) {
            eventToUpdate.setAnnotation(UpdateByAdminDtoevent.getAnnotation());
        } else {
            eventToUpdate.setAnnotation(event.getAnnotation());
        }

        if (!(UpdateByAdminDtoevent.getCategory() == null)) {
            eventToUpdate.setCategory(UpdateByAdminDtoevent.getCategory());
        } else {
            eventToUpdate.setCategory(event.getCategory());
        }

        if (!(UpdateByAdminDtoevent.getDescription() == null)) {
            eventToUpdate.setDescription(UpdateByAdminDtoevent.getDescription());
        } else {
            eventToUpdate.setDescription(event.getDescription());
        }

        if (!(UpdateByAdminDtoevent.getCreatedOn() == null)) {
            eventToUpdate.setCreatedOn(UpdateByAdminDtoevent.getCreatedOn());
        } else {
            eventToUpdate.setCreatedOn(event.getCreatedOn());
        }

        if (!(UpdateByAdminDtoevent.getEventDate() == null)) {
            eventToUpdate.setEventDate(UpdateByAdminDtoevent.getEventDate());
        } else {
            eventToUpdate.setEventDate(event.getEventDate());
        }

        if (!(UpdateByAdminDtoevent.getLocation() == null)) {
            eventToUpdate.setLon(UpdateByAdminDtoevent.getLocation().getLon());
            eventToUpdate.setLat(UpdateByAdminDtoevent.getLocation().getLat());
        } else {
            eventToUpdate.setLon(event.getLon());
            eventToUpdate.setLat(event.getLat());
        }

        if (!(UpdateByAdminDtoevent.getPaid() == null)) {
            eventToUpdate.setPaid(UpdateByAdminDtoevent.getPaid());
        } else {
            eventToUpdate.setPaid(event.isPaid());
        }

        if (!(UpdateByAdminDtoevent.getParticipantLimit() == null)) {
            eventToUpdate.setParticipantLimit(UpdateByAdminDtoevent.getParticipantLimit());
        } else {
            eventToUpdate.setParticipantLimit(event.getParticipantLimit());
        }

        if (!(UpdateByAdminDtoevent.getRequestModeration() == null)) {
            eventToUpdate.setRequestModeration(UpdateByAdminDtoevent.getRequestModeration());
        } else {
            eventToUpdate.setRequestModeration(event.isRequestModeration());
        }

        if (!(UpdateByAdminDtoevent.getTitle() == null)) {
            eventToUpdate.setTitle(UpdateByAdminDtoevent.getTitle());
        } else {
            eventToUpdate.setTitle(event.getTitle());
        }

        eventToUpdate.setState(event.getState());
        eventToUpdate.setInitiator(event.getInitiator());
        eventToUpdate.setConfirmedRequests(event.getConfirmedRequests());
        eventToUpdate.setPublishedOn(event.getPublishedOn());

        return eventToUpdate;

    }

}
