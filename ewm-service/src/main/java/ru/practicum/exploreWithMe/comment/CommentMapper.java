package ru.practicum.exploreWithMe.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.exploreWithMe.comment.dto.CommentDto;
import ru.practicum.exploreWithMe.comment.dto.CommentFullDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment transformCommentDtoToComment(CommentDto commentDto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "eventId", source = "event.id")
    CommentFullDto transformCommentToCommentFullDto(Comment comment);
}
