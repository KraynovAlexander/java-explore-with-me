package ru.practicum.emw.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.events.model.LocationEvent;
import ru.practicum.emw.users.dto.UserInitiatorDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDtoEvent {

    private Long id;

    private String annotation;

    private DtoCategory category;

    private Long confirmedRequests;

    private LocalDateTime createdOn;

    private String description;

    private String eventDate;

    private UserInitiatorDto initiator;

    private LocationEvent location;

    private boolean paid;

    private Long participantLimit;

    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private String state;

    private String title;

    private Long views;

}
