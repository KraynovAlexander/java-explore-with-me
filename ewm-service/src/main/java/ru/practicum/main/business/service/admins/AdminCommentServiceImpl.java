package ru.practicum.main.business.service.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.business.helper.SetterParamsToCommentService;
import ru.practicum.main.comment.dto.CommentToGetDto;
import ru.practicum.main.comment.exception.CommentStateException;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.model.CommentState;
import ru.practicum.main.comment.service.AdminCommentService;
import ru.practicum.main.comment.service.CommentService;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentService commentService;

    private final SetterParamsToCommentService setterParamsToCommentService;

    @Override
    public List<CommentToGetDto> getCommentsToModerateByPages(Integer from, Integer size) {

        return commentService.getCommentsToModerateByPages(from, size)
                .stream()
                .map(CommentMapper::toGetDto)
                .peek(setterParamsToCommentService::setOwnerAndEventToComment)
                .collect(Collectors.toList());

    }

    @Override
    public CommentToGetDto publishCommentByAdmin(Long commentId) {

        Comment comment = commentService.getCommentById(commentId);

        if (comment.getCommentState() != CommentState.PENDING) {
            log.error("AdminCommentService.publicCommentByAdmin: состояние комментария должно находиться в ОЖИДАНИИ, " +
                            "а нет {}",
                    comment.getCommentState());
            throw new CommentStateException("неправильное состояние комментария");
        }

        comment.setCommentState(CommentState.PUBLISHED);
        comment = commentService.updateComment(comment);

        return setterParamsToCommentService.setOwnerAndEventToComment(CommentMapper.toGetDto(comment));
    }

    @Override
    public CommentToGetDto rejectCommentByAdmin(Long commentId) {

        Comment comment = commentService.getCommentById(commentId);

        if (comment.getCommentState() != CommentState.PENDING) {
            log.error("AdminCommentService.rejectCommentByAdmin: состояние комментария должно быть ОЖИДАЮЩИМ, а нет {}",
                    comment.getCommentState());
            throw new CommentStateException("неправильное состояние комментария");
        }

        comment.setCommentState(CommentState.REJECTED);
        comment = commentService.updateComment(comment);

        return setterParamsToCommentService.setOwnerAndEventToComment(CommentMapper.toGetDto(comment));

    }
}