package ru.practicum.exploreWithMe.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.exploreWithMe.constant.EwmConstants;
import ru.practicum.exploreWithMe.user.dto.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentShortDto {
    private Long id;
    private String text;
    private UserShortDto user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    private LocalDateTime updatedOn;
}
