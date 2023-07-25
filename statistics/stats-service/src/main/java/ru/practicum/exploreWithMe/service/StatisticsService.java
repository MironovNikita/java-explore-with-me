package ru.practicum.exploreWithMe.service;

import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticsService {
    EndpointHitDto createEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStatistics(Timestamp start, Timestamp end, List<String> uris, boolean unique);
}
