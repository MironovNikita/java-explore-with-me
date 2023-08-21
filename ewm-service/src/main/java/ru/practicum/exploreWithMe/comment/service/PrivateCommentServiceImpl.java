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
import ru.practicum.exploreWithMe.common.exception.EventStateException;
import ru.practicum.exploreWithMe.common.exception.InvalidUserException;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;
import ru.practicum.exploreWithMe.common.exception.RequestParametersException;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.user.User;
import ru.practicum.exploreWithMe.user.UserRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentFullDto create(Long userId, CommentDto commentDto) {
        User user = checkUserExistence(userId);
        Event event = checkEventExistence(commentDto.getEventId());

        if (!event.getState().equals(EventState.PUBLISHED)) {
            log.error("Событие {} не опубликовано. К нему нельзя оставить комментарий", event.getTitle());
            throw new EventStateException(
                    String.format("Событие %s не опубликовано. К нему нельзя оставить комментарий", event.getTitle()));
        }

        Comment comment = commentMapper.transformCommentDtoToComment(commentDto);
        comment.setUser(user);
        comment.setEvent(event);
        comment.setCreatedOn(LocalDateTime.now());

        return commentMapper.transformCommentToCommentFullDto(commentRepository.save(comment));
    }

    @Override
    public CommentFullDto update(Long userId, Long commentId, CommentDto commentDto) {
        checkUserExistence(userId);
        Comment comment = checkCommentExistence(commentId);

        if (!comment.getUser().getId().equals(userId)) {
            log.error("Пользователь с ID {} не является создателем комментария с ID {}", userId, commentId);
            throw new InvalidUserException(userId, comment.getUser().getId());
        }

        if (LocalDateTime.now().isAfter(comment.getCreatedOn().plusHours(5))) {
            log.error("Время редактирования комментария (5ч) истекло. \nВремя создания комментария: {}.\n" +
                    "Текущее время: {}", comment.getCreatedOn(), LocalDateTime.now());
            throw new RequestParametersException("возможное время изменения комментария истекло");
        }

        comment.setText(commentDto.getText());
        comment.setUpdatedOn(LocalDateTime.now());

        return commentMapper.transformCommentToCommentFullDto(comment);
    }

    @Override
    public void delete(Long userId, Long commentId) {
        checkUserExistence(userId);
        Comment comment = checkCommentExistence(commentId);

        if (!comment.getUser().getId().equals(userId)) {
            log.error("Пользователь с ID {} не является создателем комментария с ID {}", userId, commentId);
            throw new InvalidUserException(userId, comment.getUser().getId());
        }

        commentRepository.deleteById(commentId);
    }

    private User checkUserExistence(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("Ошибка получения пользователя по ID {}, такого ID не существует!", userId);
            throw new ObjectNotFoundException("Пользователь", userId);
        });
    }

    private Event checkEventExistence(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Ошибка получения события по ID {}, такого ID не существует!", eventId);
            throw new ObjectNotFoundException("Событие", eventId);
        });
    }

    private Comment checkCommentExistence(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> {
            log.error("Ошибка получения события по ID {}, такого ID не существует!", commentId);
            throw new ObjectNotFoundException("Событие", commentId);
        });
    }
}
