package ru.practicum.main.comment.service;



import ru.practicum.main.comment.dto.CommentToGetDto;

import java.util.List;

public interface AdminCommentService {
    List<CommentToGetDto> getCommentsToModerateByPages(Integer from, Integer size);

    CommentToGetDto publishCommentByAdmin(Long commentId);

    CommentToGetDto rejectCommentByAdmin(Long commentId);
}