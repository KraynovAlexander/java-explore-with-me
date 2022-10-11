package ru.practicum.main.events.dto;

import lombok.Data;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.users.dto.UserInitiatorDto;

import java.time.LocalDateTime;

@Data
public class EventToCompilationDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    private LocalDateTime eventDate;

    private UserInitiatorDto initiator;

    private boolean paid;

    private String title;

    private Long views;

}
