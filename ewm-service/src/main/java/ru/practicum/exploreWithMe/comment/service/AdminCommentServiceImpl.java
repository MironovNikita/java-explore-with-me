package ru.practicum.exploreWithMe.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.comment.Comment;
import ru.practicum.exploreWithMe.comment.CommentMapper;
import ru.practicum.exploreWithMe.comment.CommentRepository;
import ru.practicum.exploreWithMe.comment.dto.CommentDto;
import ru.practicum.exploreWithMe.comment.dto.CommentFullDto;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentFullDto redactComment(Long commentId, CommentDto commentDto) {
        Comment comment = checkCommentExistence(commentId);

        comment.setText(commentDto.getText());
        comment.setUpdatedOn(LocalDateTime.now());

        return commentMapper.transformCommentToCommentFullDto(comment);
    }

    @Override
    public void delete(Long commentId) {
        checkCommentExistence(commentId);
        commentRepository.deleteById(commentId);
    }

    private Comment checkCommentExistence(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> {
            log.error("Ошибка получения события по ID {}, такого ID не существует!", commentId);
            throw new ObjectNotFoundException("Событие", commentId);
        });
    }
}
