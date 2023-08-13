package ru.practicum.exploreWithMe.event.service;

import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.enums.EventSortValue;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    public EventFullDto get(Long eventId, HttpServletRequest httpServletRequest);

    public List<EventShortDto> getAllByPublicFilters(String text, List<Long> categoryIds, Boolean paid,
                                                     LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                     Boolean onlyAvailable, EventSortValue sort, Integer from,
                                                     Integer size, HttpServletRequest httpServletRequest);
}
