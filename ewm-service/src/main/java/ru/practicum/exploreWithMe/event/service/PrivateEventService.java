package ru.practicum.exploreWithMe.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.exploreWithMe.event.dto.*;
import ru.practicum.exploreWithMe.request.dto.RequestDto;

import java.util.List;

public interface PrivateEventService {
    EventFullDto create(Long userId, CreateEventDto eventDto);

    EventFullDto update(Long userId, Long eventId, UpdateEventDtoByUser dtoByUser);

    EventFullDto get(Long userId, Long eventId);

    List<EventShortDto> getAllEventsOfUser(Long userId, Pageable pageable);

    EventRequestStatusUpdateResultDto updateRequestsOfEvent(Long userId, Long eventId,
                                                            EventRequestStatusUpdateRequestDto updateRequestDto);

    List<RequestDto> getRequestsOfEvent(Long userId, Long eventId);
}
