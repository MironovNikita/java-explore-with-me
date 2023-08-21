package ru.practicum.exploreWithMe.comment.service;

import ru.practicum.exploreWithMe.comment.dto.CommentDto;
import ru.practicum.exploreWithMe.comment.dto.CommentFullDto;

public interface PrivateCommentService {
    CommentFullDto create(Long userId, CommentDto commentDto);

    CommentFullDto update(Long userId, Long commentId, CommentDto commentDto);

    void delete(Long userId, Long commentId);
}
