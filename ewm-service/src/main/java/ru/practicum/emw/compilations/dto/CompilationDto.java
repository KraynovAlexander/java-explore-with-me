package ru.practicum.emw.compilations.dto;

import lombok.Data;
import ru.practicum.emw.events.dto.CompilationDtoEvent;


import java.util.List;

@Data
public class CompilationDto {

    private Long id;

    private List<CompilationDtoEvent> events;

    private boolean pinned;

    private String title;

}
