package ru.practicum.main.business.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.CommentToGetDto;
import ru.practicum.main.events.service.EventService;
import ru.practicum.main.users.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SetterParamsToCommentService {

    private final UserService userService;

    private final EventService eventService;


    public CommentToGetDto setOwnerAndEventToComment(CommentToGetDto commentToGetDto) {

        commentToGetDto.getOwner().setName(userService.getUserNameById(commentToGetDto.getOwner().getId()));
        commentToGetDto.getEvent().setTitle(eventService.getEventTitleById(commentToGetDto.getEvent().getId()));

        return commentToGetDto;

    }

}