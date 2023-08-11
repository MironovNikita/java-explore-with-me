package ru.practicum.exploreWithMe.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.constant.EwmConstants;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.user.dto.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private String title;
    private long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EwmConstants.DATE_FORMAT)
    private LocalDateTime publishedOn;
    private UserShortDto initiator;
    private EventState state;
    private long views;
}
