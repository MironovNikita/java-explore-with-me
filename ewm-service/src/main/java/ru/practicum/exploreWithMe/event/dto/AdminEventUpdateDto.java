package ru.practicum.exploreWithMe.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.event.enums.EventStateAdmin;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminEventUpdateDto extends UpdateEventDto {
    private EventStateAdmin stateAction;
}
