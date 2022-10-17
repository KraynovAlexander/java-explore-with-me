package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.exception.CommentException;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.model.CommentState;
import ru.practicum.main.comment.repository.CommentRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment getCommentById(Long commentId) {

        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("comment not found"));
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsToModerateByPages(Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("created").ascending());

        return commentRepository.findCommentsByCommentState(CommentState.PENDING, pageable).getContent();
    }

    @Override
    public Optional<Comment> findCommentByOwnerIdAndEventId(Long ownerId, Long eventId) {
        return commentRepository.findCommentByOwnerIdAndEventId(ownerId, eventId);
    }

    @Override
    public Comment addNewComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByOwnerIdByTime(Long userId, LocalDateTime start, LocalDateTime end,
                                                    Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("created").descending());

        return commentRepository.findCommentsByOwnerIdAndCreatedBetween(userId, start, end, pageable).getContent();

    }

    @Override
    public List<Comment> getCommentsByEventId(Long eventId, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("created").descending());

        return commentRepository.findCommentsByEventId(eventId, pageable).getContent();
    }

    @Override
    public void deleteCommentById(Long commentId) {

        if (!commentRepository.existsById(commentId)) {
            log.error("CommentService.deleteCommentById: comment with id={} not exist", commentId);
            throw new CommentException("comment not found");
        }

        commentRepository.deleteById(commentId);

    }
}