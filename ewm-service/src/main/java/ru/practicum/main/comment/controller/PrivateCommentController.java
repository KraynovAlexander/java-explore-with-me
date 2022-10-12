package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.categories.model.Create;
import ru.practicum.main.comment.dto.CommentToCreateDto;
import ru.practicum.main.comment.dto.CommentToGetDto;
import ru.practicum.main.comment.service.PrivateCommentService;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("users/{userId}/comments")
public class PrivateCommentController {

    private final PrivateCommentService privateCommentService;

    @PostMapping
    public CommentToGetDto addNewComment(@PathVariable Long userId,
                                         @Validated(Create.class) CommentToCreateDto commentToCreateDto) {

        log.info("PrivateCommentController.addNewComment: получен запрос на добавление нового комментария " +
                "на мероприятие с id={} пользователем с id={}", commentToCreateDto.getEventId(), userId);

        return privateCommentService.addNewComment(userId, commentToCreateDto);

    }

    @PatchMapping("/{commentId}")
    public CommentToGetDto updateComment(@PathVariable Long userId,
                                         @Valid @NotBlank String text, @PathVariable Long commentId) {

        log.info("PrivateCommentController.updateComment: получен запрос на обновление комментария " +
                "с id={} пользователем с id={}", commentId, userId);

        return privateCommentService.updateComment(userId, commentId, text);

    }

    @GetMapping("/{commentId}")
    public CommentToGetDto getCommentById(@PathVariable Long userId, @PathVariable Long commentId) {

        log.info("PrivateCommentController.getCommentById: получен запрос комментария с id={} " +
                "пользователем с id={}", commentId, userId);

        return privateCommentService.getCommentById(userId, commentId);

    }

    @GetMapping
    public List<CommentToGetDto> getCommentsByUserIdByPeriod(@PathVariable Long userId,
                                                             @RequestParam String startTime,
                                                             @RequestParam String endTime,
                                                             @PositiveOrZero @RequestParam Integer from,
                                                             @Positive Integer size) {

        log.info("PrivateCommentController.getCommentsByUserIdByPeriod: получен запрос на получение пользователя " +
                "комментарии с id={}", userId);

        return privateCommentService.getCommentsByUserId(userId, startTime, endTime, from, size);

    }

    @GetMapping("/events/{eventId}/comments")
    public List<CommentToGetDto> getCommentsByEventId(@PathVariable Long userId, @PathVariable Long eventId,
                                                      @PositiveOrZero @RequestParam Integer from,
                                                      @Positive Integer size) {

        log.info("PrivateCommentController.getCommentsByEventId: получен запрос на получение комментариев " +
                "по событию с id={} пользователем с id={}", eventId, userId);

        return privateCommentService.getCommentsByEventId(userId, eventId, from, size);

    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentByIdByOwner(@PathVariable Long userId, @PathVariable Long commentId) {

        log.info("PrivateCommentController.deleteCommentByIdOwner: получен запрос на удаление комментария " +
                "с id={} пользователем с id={}", commentId, userId);

        privateCommentService.deleteCommentByIdByOwner(userId, commentId);

    }

}