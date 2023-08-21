package ru.practicum.exploreWithMe.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.comment.dto.CommentDto;
import ru.practicum.exploreWithMe.comment.dto.CommentFullDto;
import ru.practicum.exploreWithMe.comment.service.PrivateCommentService;
import ru.practicum.exploreWithMe.common.validation.Create;
import ru.practicum.exploreWithMe.common.validation.Update;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/comments")
public class PrivateCommentController {
    private final PrivateCommentService privateCommentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentFullDto create(@PathVariable Long userId,
                                 @Validated(Create.class) @RequestBody CommentDto commentDto) {
        log.info("PrivateCommentController: Запрос на создание комментария \"{}\" пользователем с ID {}",
                commentDto.getText(), userId);
        return privateCommentService.create(userId, commentDto);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentFullDto update(@PathVariable Long userId, @PathVariable Long commentId,
                                 @Validated(Update.class) @RequestBody CommentDto commentDto) {
        log.info("PrivateCommentController: Запрос пользователя с ID {} на обновления комментария с ID {}. Новый" +
                "текст комментария: {}", userId, commentId, commentDto.getText());
        return privateCommentService.update(userId, commentId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("PrivateCommentController: Запрос на удаление комментария по ID {} пользователем с ID {}",
                commentId, userId);
        privateCommentService.delete(userId, commentId);
    }
}
