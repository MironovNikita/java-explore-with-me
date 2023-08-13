package ru.practicum.exploreWithMe.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.constant.EwmConstants;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.enums.EventSortValue;
import ru.practicum.exploreWithMe.event.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService publicEventService;

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto get(@PathVariable Long eventId, HttpServletRequest httpServletRequest) {
        log.info("PublicEventController: Запрос на получение события по ID: {}", eventId);
        return publicEventService.get(eventId, httpServletRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllByPublicFilters(
            @RequestParam(required = false) String text,
            @RequestParam(name = "categories", required = false) List<Long> categoryIds,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = EwmConstants.DATE_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = EwmConstants.DATE_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) EventSortValue sort,
            @PositiveOrZero(message = "Минимальное значение индекса: 0")
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive(message = "Минимальное количество элементов: 1")
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            HttpServletRequest httpServletRequest) {
        log.info("PublicEventController: Запрос на получение всех событий по фильтрам: \n" +
                "- text: {};\n" +
                "- categoryIds: {};\n" +
                "- paid: {};\n" +
                "- rangeStart: {};\n" +
                "- rangeEnd: {};\n" +
                "- onlyAvailable: {};\n" +
                "- sort: {};\n" +
                "- from: {};\n" +
                "- size: {}.", text, categoryIds, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return publicEventService.getAllByPublicFilters(text, categoryIds, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, httpServletRequest);
    }
}
