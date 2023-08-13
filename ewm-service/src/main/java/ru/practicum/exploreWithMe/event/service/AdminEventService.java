package ru.practicum.exploreWithMe.event.service;

import ru.practicum.exploreWithMe.event.dto.AdminEventUpdateDto;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    EventFullDto redactEvent(Long eventId, AdminEventUpdateDto eventUpdateDto);

    List<EventFullDto> getAllEventsByAdminFilters(List<Long> userIds, List<EventState> states, List<Long> categoryIds,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
                                                  Integer size);
}
