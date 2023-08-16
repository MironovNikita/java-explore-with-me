package ru.practicum.exploreWithMe.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.comment.dto.CommentDto;
import ru.practicum.exploreWithMe.comment.dto.CommentFullDto;
import ru.practicum.exploreWithMe.comment.service.AdminCommentService;
import ru.practicum.exploreWithMe.common.validation.Update;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/comments")
public class AdminCommentController {
    private final AdminCommentService adminCommentService;

    @PostMapping("/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentFullDto redactComment(@PathVariable Long commentId,
                                        @Validated(Update.class) @RequestBody CommentDto commentDto) {
        log.info("AdminCommentController: Запрос на обновление комментария по ID {} на новый: {}", commentId,
                commentDto.getText());
        return adminCommentService.redactComment(commentId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId) {
        log.info("AdminCommentController: Запрос на удаление комментария по ID {}", commentId);
        adminCommentService.delete(commentId);
    }
}
