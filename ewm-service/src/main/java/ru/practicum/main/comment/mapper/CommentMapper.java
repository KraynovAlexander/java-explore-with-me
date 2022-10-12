package ru.practicum.main.comment.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.main.comment.dto.CommentToCreateDto;
import ru.practicum.main.comment.dto.CommentToGetDto;
import ru.practicum.main.comment.dto.CommentToGetEventDto;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.events.dto.EventToCommentDto;
import ru.practicum.main.users.dto.UserInitiatorDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment fromCreateDto(CommentToCreateDto commentToCreateDto) {

        Comment comment = new Comment();

        comment.setText(commentToCreateDto.getText());
        comment.setEventId(commentToCreateDto.getEventId());

        return comment;

    }

    public static CommentToGetDto toGetDto(Comment comment) {

        CommentToGetDto commentToGetDto = new CommentToGetDto();

        commentToGetDto.setId(comment.getId());
        commentToGetDto.setOwner(new UserInitiatorDto(comment.getOwnerId()));
        commentToGetDto.setEvent(new EventToCommentDto(comment.getEventId()));
        commentToGetDto.setText(comment.getText());
        commentToGetDto.setCreated(comment.getCreated());

        return commentToGetDto;

    }

    public static CommentToGetEventDto toGetEventDto(Comment comment) {

        CommentToGetEventDto commentToGetEventDto = new CommentToGetEventDto();

        commentToGetEventDto.setText(comment.getText());
        commentToGetEventDto.setState(comment.getCommentState());
        commentToGetEventDto.setCreated(comment.getCreated());

        return commentToGetEventDto;

    }

}