package ru.practicum.exploreWithMe.comment.service;

import ru.practicum.exploreWithMe.comment.dto.CommentDto;
import ru.practicum.exploreWithMe.comment.dto.CommentFullDto;

public interface AdminCommentService {
    CommentFullDto redactComment(Long commentId, CommentDto commentDto);

    void delete(Long commentId);
}
