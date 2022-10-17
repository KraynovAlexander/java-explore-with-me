package ru.practicum.main.comment.dto;

import lombok.Data;
import ru.practicum.main.events.dto.EventToCommentDto;
import ru.practicum.main.users.dto.UserInitiatorDto;


import java.time.LocalDateTime;

@Data
public class CommentToGetDto {

    private Long id;

    private String text;

    private UserInitiatorDto owner;

    private EventToCommentDto event;

    private LocalDateTime created;

}