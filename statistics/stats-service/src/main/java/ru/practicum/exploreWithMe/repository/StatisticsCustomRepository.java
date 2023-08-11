package ru.practicum.exploreWithMe.repository;

import ru.practicum.exploreWithMe.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsCustomRepository {
    List<ViewStats> getStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
