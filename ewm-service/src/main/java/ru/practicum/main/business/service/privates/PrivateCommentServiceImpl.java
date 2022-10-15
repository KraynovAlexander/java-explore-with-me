package ru.practicum.main.business.service.privates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.business.helper.Checker;
import ru.practicum.main.business.helper.DateTimeStringConverter;
import ru.practicum.main.business.helper.SetterParamsToCommentService;
import ru.practicum.main.comment.dto.CommentToCreateDto;
import ru.practicum.main.comment.dto.CommentToGetDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.model.CommentState;
import ru.practicum.main.comment.service.CommentService;
import ru.practicum.main.comment.service.PrivateCommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentService commentService;

    private final SetterParamsToCommentService setterParamsToCommentService;

    private final Checker checker;

    @Override
    public CommentToGetDto addNewComment(Long userId, CommentToCreateDto commentToCreateDto) {

        checker.userExistChecker(userId);

        checker.eventExistChecker(commentToCreateDto.getEventId());

        if (commentService.findCommentByOwnerIdAndEventId(userId, commentToCreateDto.getEventId()).isPresent()) {
            log.error("PrivateCommentService.addNewComment: пользователь с id={} уже добавили комментарий к событию " +
                    "c id={}", userId, commentToCreateDto.getEventId());
        }

        Comment comment = CommentMapper.fromCreateDto(commentToCreateDto);

        comment.setCommentState(CommentState.PENDING);

        comment = commentService.addNewComment(comment);

        return setterParamsToCommentService.setOwnerAndEventToComment(CommentMapper.toGetDto(comment));

    }

    @Override
    public CommentToGetDto updateComment(Long userId, Long commentId, String text) {

        checker.userExistChecker(userId);

        Comment comment = commentService.getCommentById(commentId);

        comment.setText(text);

        comment.setCommentState(CommentState.PENDING);

        comment = commentService.updateComment(comment);

        return setterParamsToCommentService.setOwnerAndEventToComment(CommentMapper.toGetDto(comment));

    }

    @Override
    public CommentToGetDto getCommentById(Long userId, Long commentId) {

        checker.userExistChecker(userId);

        return setterParamsToCommentService
                .setOwnerAndEventToComment(CommentMapper.toGetDto(commentService.getCommentById(commentId)));
    }

    @Override
    public List<CommentToGetDto> getCommentsByUserId(Long userId, String startTime, String endTime,
                                                     Integer from, Integer size) {

        checker.userExistChecker(userId);

        LocalDateTime start = DateTimeStringConverter.fromFormattedString(startTime);
        LocalDateTime end = DateTimeStringConverter.fromFormattedString(endTime);

        return commentService.getCommentsByOwnerIdByTime(userId, start, end, from, size)
                .stream()
                .map(CommentMapper::toGetDto)
                .peek(setterParamsToCommentService::setOwnerAndEventToComment)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentToGetDto> getCommentsByEventId(Long userId, Long eventId, Integer from, Integer size) {

        checker.userExistChecker(userId);
        checker.eventExistChecker(eventId);

        return commentService.getCommentsByEventId(eventId, from, size)
                .stream()
                .map(CommentMapper::toGetDto)
                .peek(setterParamsToCommentService::setOwnerAndEventToComment)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteCommentByIdByOwner(Long userId, Long commentId) {

        checker.userExistChecker(userId);

        commentService.deleteCommentById(commentId);

    }

}