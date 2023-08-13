package ru.practicum.exploreWithMe.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.exploreWithMe.mapper.ViewStatsMapper;
import ru.practicum.exploreWithMe.model.QEndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StatisticsRepositoryImpl implements StatisticsCustomRepository {
    private final ViewStatsMapper viewStatsMapper;
    private final EntityManager entityManager;

    @Override
    public List<ViewStats> getStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        QEndpointHit qEndpointHit = QEndpointHit.endpointHit;
        BooleanExpression relevantParams = qEndpointHit.timestamp.between(start, end);

        if (uris != null && !uris.isEmpty()) {
            relevantParams = relevantParams.and(qEndpointHit.uri.in(uris));
        }

        return new JPAQuery<Tuple>(entityManager).select(qEndpointHit.app, qEndpointHit.uri,
                        unique ? qEndpointHit.ip.countDistinct() : Expressions.ONE.count()).from(qEndpointHit)
                .where(relevantParams)
                .groupBy(qEndpointHit.app, qEndpointHit.uri)
                .orderBy(Expressions.THREE.desc())
                .stream().map(viewStatsMapper::transformTupleToViewStats).collect(Collectors.toUnmodifiableList());
    }
}
