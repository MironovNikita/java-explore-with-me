package ru.practicum.exploreWithMe.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FullCompilationDto {
    private Long id;
    private boolean pinned;
    private String title;
    private List<EventFullDto> events;
}
