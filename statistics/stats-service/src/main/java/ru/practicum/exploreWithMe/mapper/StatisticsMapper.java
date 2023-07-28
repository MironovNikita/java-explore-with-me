package ru.practicum.exploreWithMe.mapper;

import org.mapstruct.Mapper;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {
    EndpointHit endpointHitDtoToEndpointHit(EndpointHitDto endpointHitDto);

    EndpointHitDto endpointHitToEndpointHitDto(EndpointHit endpointHit);

    List<ViewStatsDto> viewStatsListToListViewStatsDto(List<ViewStats> viewStatsList);

    ViewStatsDto viewStatsDtoToViewStats(ViewStats viewStats);
}
