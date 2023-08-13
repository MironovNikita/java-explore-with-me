package ru.practicum.exploreWithMe.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.constant.EwmConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateEventDto {
    @NotBlank(message = "Краткое описание события не может быть пустым")
    @Size(min = 20, max = 2000, message = "Минимальный размер описания - 20 символов, максимальный - 3000 символов")
    private String annotation;

    @NotNull(message = "Категория события не может быть пустой")
    private Long category;

    @NotBlank(message = "Описание события не может быть пустым")
    @Size(min = 20, max = 7000, message = "Минимальный размер описания - 20 символов, максимальный - 7000 символов")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    @NotNull(message = "Событие не может быть создано без даты")
    private LocalDateTime eventDate;

    @Valid
    @NotNull(message = "Необходимы координаты места события")
    private Location location;

    private boolean paid;

    @PositiveOrZero(message = "Количество участников события не может быть отрицательным")
    private int participantLimit;

    private boolean requestModeration = true;

    @NotBlank(message = "Заголовок события не может быть пустой")
    @Size(min = 3, max = 120, message = "Минимальный размер заголовка события - 3 символа, максимальный - 120 символов")
    private String title;
}
