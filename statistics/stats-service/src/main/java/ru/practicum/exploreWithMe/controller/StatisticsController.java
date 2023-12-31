package ru.practicum.exploreWithMe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.constant.EwmConstants;
import ru.practicum.exploreWithMe.service.StatisticsService;
import ru.practicum.exploreWithMe.validation.Create;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto createEndpointHit(@Validated(Create.class) @RequestBody EndpointHitDto endpointHitDto) {
        log.info("Создание записи endpoint пользователем с ip {}", endpointHitDto.getIp());
        return statisticsService.createEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> getStatistics(@RequestParam
                                            @DateTimeFormat(pattern = EwmConstants.DATE_FORMAT) LocalDateTime start,
                                            @RequestParam
                                            @DateTimeFormat(pattern = EwmConstants.DATE_FORMAT) LocalDateTime end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Получение статистики с параметрами \n start: {}, \n end: {}, \n uris: {}, \n unique: {}",
                start, end, uris, unique);
        return statisticsService.getStatistics(start, end, uris, unique);
    }
}
