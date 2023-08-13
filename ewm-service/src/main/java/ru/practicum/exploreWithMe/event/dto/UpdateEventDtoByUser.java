package ru.practicum.exploreWithMe.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.event.enums.EventStateUser;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateEventDtoByUser extends UpdateEventDto {
    private EventStateUser stateAction;
}
