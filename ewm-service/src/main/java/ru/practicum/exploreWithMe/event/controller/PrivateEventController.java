package ru.practicum.exploreWithMe.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.common.pagination.Pagination;
import ru.practicum.exploreWithMe.event.dto.*;
import ru.practicum.exploreWithMe.event.service.PrivateEventService;
import ru.practicum.exploreWithMe.request.dto.RequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final PrivateEventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId, @Validated @RequestBody CreateEventDto eventDto) {
        log.info("PrivateEventController: Запрос на создание события: {}", eventDto.getTitle());
        return eventService.create(userId, eventDto);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto update(@PathVariable Long userId, @PathVariable Long eventId,
                               @Validated @RequestBody UpdateEventDtoByUser dtoByUser) {
        log.info("PrivateEventController: Запрос на обновление события с ID: {}", eventId);
        return eventService.update(userId, eventId, dtoByUser);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto get(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("PrivateEventController: Запрос на получение информации о событии с ID: {}", eventId);
        return eventService.get(userId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllEventsOfUser(@PathVariable Long userId,
                                                  @PositiveOrZero(message = "Минимальное значение индекса: 0")
                                                  @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                  @Positive(message = "Минимальное количество элементов: 1")
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("PrivateEventController: Запрос на получение списка событий пользователя с ID: {}", userId);
        return eventService.getAllEventsOfUser(userId, Pagination.splitByPages(from, size));
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResultDto updateRequestsOfEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Validated @RequestBody EventRequestStatusUpdateRequestDto updateRequestDto) {
        log.info("PrivateEventController: Запрос на изменение статуса заявок на участие в событии с ID: {}", eventId);
        return eventService.updateRequestsOfEvent(userId, eventId, updateRequestDto);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> getRequestsOfEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("PrivateEventController: Запрос на получение информации о запросах участия в событии с ID: {}",
                eventId);
        return eventService.getRequestsOfEvent(userId, eventId);
    }
}
