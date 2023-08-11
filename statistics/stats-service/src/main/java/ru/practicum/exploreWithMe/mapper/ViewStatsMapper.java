package ru.practicum.exploreWithMe.mapper;

import com.querydsl.core.Tuple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.model.ViewStats;

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {
    @Mapping(target = "app", expression = "java(tuple.get(0, String.class))")
    @Mapping(target = "uri", expression = "java(tuple.get(1, String.class))")
    @Mapping(target = "hits", expression = "java(tuple.get(2, Long.class))")
    ViewStats transformTupleToViewStats(Tuple tuple);

    ViewStatsDto transformViewStatsToViewStatsDto(ViewStats viewStats);
}
