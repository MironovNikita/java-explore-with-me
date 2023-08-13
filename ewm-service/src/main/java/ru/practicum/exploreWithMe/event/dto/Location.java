package ru.practicum.exploreWithMe.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location {
    @NotNull(message = "Широта не может быть пустой")
    private Float lat;
    @NotNull(message = "Долгота не может быть пустой")
    private Float lon;
}
