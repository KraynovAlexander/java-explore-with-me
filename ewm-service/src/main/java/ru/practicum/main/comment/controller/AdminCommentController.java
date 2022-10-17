package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentToGetDto;
import ru.practicum.main.comment.service.AdminCommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
@Slf4j
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @GetMapping
    public List<CommentToGetDto> getCommentsToModerateByPages(
            @PositiveOrZero @RequestParam (name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("AdminCommentController.getCommentToModerateByPages: получен запрос на получение " +
                "комментарии для модерации");

        return adminCommentService.getCommentsToModerateByPages(from, size);

    }

    @PatchMapping("/{commentId}/publish")
    public CommentToGetDto publishCommentByAdmin(@PathVariable Long commentId) {

        log.info("AdminCommentController.publishCommentByAdmin: получен запрос на публикацию " +
                "комментарий с id={} администратором", commentId);

        return adminCommentService.publishCommentByAdmin(commentId);

    }

    @PatchMapping("/{commentId}/reject")
    public CommentToGetDto rejectCommentByAdmin(@PathVariable Long commentId) {

        log.info("AdminCommentController.rejectCommentByAdmin: получен запрос на отклонение " +
                "комментарий с id={} администратором", commentId);

        return adminCommentService.rejectCommentByAdmin(commentId);

    }

}