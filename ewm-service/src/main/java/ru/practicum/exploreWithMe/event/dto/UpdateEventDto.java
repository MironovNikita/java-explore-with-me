package ru.practicum.exploreWithMe.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.constant.EwmConstants;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateEventDto {
    @Size(min = 20, max = 2000, message = "Минимальный размер описания - 20 символов, максимальный - 3000 символов")
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = "Минимальный размер описания - 20 символов, максимальный - 7000 символов")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    private LocalDateTime eventDate;
    @Valid
    private Location location;
    private Boolean paid;
    @PositiveOrZero(message = "Количество участников события не может быть отрицательным")
    private Integer participantLimit;
    private Boolean requestModeration;
    @Size(min = 3, max = 120, message = "Минимальный размер заголовка события - 3 символа, максимальный - 120 символов")
    private String title;
}
