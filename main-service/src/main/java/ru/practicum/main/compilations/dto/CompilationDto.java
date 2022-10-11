package ru.practicum.main.compilations.dto;

import lombok.Data;
import ru.practicum.main.events.dto.EventToCompilationDto;

import java.util.List;

@Data
public class CompilationDto {

    private Long id;

    private List<EventToCompilationDto> events;

    private boolean pinned;

    private String title;

}
