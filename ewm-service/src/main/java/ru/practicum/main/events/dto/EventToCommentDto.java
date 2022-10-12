package ru.practicum.main.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventToCommentDto {

    private Long id;

    private String title;

    public EventToCommentDto(Long id) {
        this.id = id;
    }
}