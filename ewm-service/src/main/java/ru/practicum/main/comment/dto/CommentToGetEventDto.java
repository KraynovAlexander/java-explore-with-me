package ru.practicum.main.comment.dto;

import lombok.Data;
import ru.practicum.main.comment.model.CommentState;


import java.time.LocalDateTime;

@Data
public class CommentToGetEventDto {

    private String text;

    private CommentState state;

    private LocalDateTime created;

}