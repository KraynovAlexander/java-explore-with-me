package ru.practicum.emw.events.dto;

import lombok.Data;
import ru.practicum.emw.categories.dto.DtoCategory;
import ru.practicum.emw.users.dto.UserInitiatorDto;

import java.time.LocalDateTime;

@Data
public class CompilationDtoEvent {

    private Long id;

    private String annotation;

    private DtoCategory category;

    private Long confirmedRequests;

    private LocalDateTime eventDate;

    private UserInitiatorDto initiator;

    private boolean paid;

    private String title;

    private Long views;

}
