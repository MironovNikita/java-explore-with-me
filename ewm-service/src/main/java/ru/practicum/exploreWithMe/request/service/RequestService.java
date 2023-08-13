package ru.practicum.exploreWithMe.request.service;

import ru.practicum.exploreWithMe.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto create(Long userId,Long eventId);

    RequestDto cancel(Long userId, Long requestId);

    List<RequestDto> getAll(Long userId);
}
