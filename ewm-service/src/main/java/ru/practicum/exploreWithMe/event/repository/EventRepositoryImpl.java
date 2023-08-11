package ru.practicum.exploreWithMe.event.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.QEvent;
import ru.practicum.exploreWithMe.event.enums.EventSortValue;
import ru.practicum.exploreWithMe.event.enums.EventState;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventCustomRepository {
    private final EntityManager entityManager;

    @Override
    public List<Event> findAllByPublicFilters(String text, List<Long> categories, Boolean paid,
                                              LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                              EventSortValue eventSortValue, Integer from, Integer size) {
        QEvent qEvent = QEvent.event;
        BooleanExpression relevantParam = Expressions.asBoolean(true).isTrue();

        if (text != null && !text.isBlank()) {
            relevantParam.and(qEvent.annotation.containsIgnoreCase(text)
                    .or(qEvent.description.containsIgnoreCase(text)));
        }
        if (categories != null && !categories.isEmpty()) {
            relevantParam = relevantParam.and(qEvent.category.id.in(categories));
        }
        if (paid != null) {
            relevantParam = relevantParam.and(qEvent.paid.eq(paid));
        }
        if (rangeStart != null && rangeEnd != null) {
            relevantParam = relevantParam.and(qEvent.eventDate.after(LocalDateTime.now()));
        }
        if (rangeStart != null) {
            relevantParam = relevantParam.and(qEvent.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            relevantParam = relevantParam.and(qEvent.eventDate.before(rangeEnd));
        }
        OrderSpecifier orderSpecifier = qEvent.id.asc();

        if (eventSortValue == EventSortValue.EVENT_DATE) {
            orderSpecifier = qEvent.eventDate.desc();
        }

        return new JPAQuery<Event>(entityManager).from(qEvent).where(relevantParam).offset(from).limit(size)
                .stream().collect(Collectors.toList());
    }

    @Override
    public List<Event> findAllByAdminFilters(List<Long> users, List<EventState> states, List<Long> categories,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
                                             Integer size) {
        QEvent qEvent = QEvent.event;
        BooleanExpression relevantParam = Expressions.asBoolean(true).isTrue();

        if (users != null && !users.isEmpty()) {
            relevantParam = relevantParam.and(qEvent.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            relevantParam = relevantParam.and(qEvent.state.in(states));
        }
        if (categories != null && !categories.isEmpty()) {
            relevantParam = relevantParam.and(qEvent.category.id.in(categories));
        }
        if (rangeStart != null && rangeEnd != null) {
            relevantParam = relevantParam.and(qEvent.eventDate.between(rangeStart, rangeEnd));
        }

        return new JPAQuery<Event>(entityManager).from(qEvent).where(relevantParam).offset(from).limit(size)
                .stream().collect(Collectors.toList());
    }
}
