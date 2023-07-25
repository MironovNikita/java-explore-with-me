package ru.practicum.exploreWithMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<EndpointHit, Long> {
    @Query(value = "SELECT new ru.practicum.exploreWithMe.model.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end AND eh.uri IN (:uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(eh.ip) DESC")
    List<ViewStats> findStatisticsAfterStartAndBeforeEndWithUris(Timestamp start, Timestamp end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.exploreWithMe.model.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end AND eh.uri IN (:uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(eh.ip) DESC")
    List<ViewStats> findStatisticsAfterStartAndBeforeEndWithUrisAndUniqueIp(Timestamp start, Timestamp end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.exploreWithMe.model.ViewStats(e.app, e.uri, COUNT(e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT (e.ip) DESC")
    List<ViewStats> findStatisticsAfterStartAndBeforeEnd(Timestamp start, Timestamp end);

    @Query(value = "SELECT new ru.practicum.exploreWithMe.model.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT (eh.ip) DESC")
    List<ViewStats> findStatisticsAfterStartAndBeforeEndWithUniqueIp(Timestamp start, Timestamp end);
}
