package ru.practicum.exploreWithMe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.service.StatisticsService;
import ru.practicum.exploreWithMe.validation.Create;

import java.sql.Timestamp;
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
    public List<ViewStatsDto> getStatistics(@RequestParam Timestamp start, @RequestParam Timestamp end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Получение статистики с параметрами \n start: {}, \n end: {}, \n uris: {}, \n unique: {}",
                start, end, uris, unique);
        return statisticsService.getStatistics(start, end, uris, unique);
    }
}
