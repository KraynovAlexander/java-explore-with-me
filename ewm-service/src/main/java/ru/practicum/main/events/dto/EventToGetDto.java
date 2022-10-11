package ru.practicum.main.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.events.model.Location;
import ru.practicum.main.users.dto.UserInitiatorDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventToGetDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    private LocalDateTime createdOn;

    private String description;

    private String eventDate;

    private UserInitiatorDto initiator;

    private Location location;

    private boolean paid;

    private Long participantLimit;

    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private String state;

    private String title;

    private Long views;

}
