package ru.practicum.exploreWithMe.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.common.validation.Create;
import ru.practicum.exploreWithMe.common.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDto {
    @NotNull(groups = Create.class, message = "Необходим ID события для добавления комментария")
    private Long eventId;
    @NotBlank(groups = {Create.class, Update.class}, message = "Необходим текст комментария")
    @Size(min = 5, max = 2500, message = "Текст комментария должен составлять от 5 до 2500 символов")
    private String text;
}
