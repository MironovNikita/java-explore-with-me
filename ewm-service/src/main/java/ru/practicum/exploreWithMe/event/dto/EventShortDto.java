package ru.practicum.exploreWithMe.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.constant.EwmConstants;
import ru.practicum.exploreWithMe.user.dto.UserShortDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventShortDto {
    private Long id;

    @Size(max = 3000, message = "Максимальный размер краткого описания - 3000 символов")
    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private Boolean paid;

    @Size(max = 200, message = "Максимальный размер заголовка события - 200 символов")
    private String title;

    private Long views;
}
