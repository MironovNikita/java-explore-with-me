package ru.practicum.exploreWithMe.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.dto.AdminEventUpdateDto;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.event.service.AdminEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class AdminEventController {
    private final AdminEventService adminEventService;

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto redactEvent(@PathVariable Long eventId,
                                    @Validated @RequestBody AdminEventUpdateDto eventUpdateDto) {
        log.info("AdminEventController: Запрос на редактирование события с ID {}", eventId);
        return adminEventService.redactEvent(eventId, eventUpdateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllEventsByFilters(
            @RequestParam(name = "users", required = false) List<Long> userIds,
            @RequestParam(required = false)List<EventState> states,
            @RequestParam(name = "categories", required = false) List<Long> categoryIds,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @PositiveOrZero(message = "Минимальное значение индекса: 0")
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive(message = "Минимальное количество элементов: 1")
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("AdminEventController: Запрос на получение всех событий по фильтрам: \n" +
                "- userIds: {};\n" +
                "- states: {};\n" +
                "- categoryIds: {};\n" +
                "- rangeStart: {};\n" +
                "- rangeEnd: {};\n" +
                "- from: {};\n" +
                "- size: {}.", userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
        return adminEventService.getAllEventsByAdminFilters(userIds, states, categoryIds, rangeStart, rangeEnd,
                from, size);
    }
}
