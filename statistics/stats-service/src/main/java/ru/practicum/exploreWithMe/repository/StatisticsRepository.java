package ru.practicum.exploreWithMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.exploreWithMe.model.EndpointHit;

public interface StatisticsRepository extends JpaRepository<EndpointHit, Long>, QuerydslPredicateExecutor<EndpointHit>,
StatisticsCustomRepository {}
