package ru.practicum.exploreWithMe.event.repository;

import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.enums.EventSortValue;
import ru.practicum.exploreWithMe.event.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventCustomRepository {
    List<Event> findAllByPublicFilters(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, EventSortValue eventSortValue, Integer from,
                                       Integer size);

    List<Event> findAllByAdminFilters(List<Long> users, List<EventState> states, List<Long> categories,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);
}
