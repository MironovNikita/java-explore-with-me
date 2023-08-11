package ru.practicum.exploreWithMe.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.exploreWithMe.event.Event;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event>,
EventCustomRepository {
    Set<Event> findAllByIdIn(List<Long> eventIds);

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);
}
