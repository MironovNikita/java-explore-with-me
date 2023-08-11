package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.StatisticsClient;
import ru.practicum.exploreWithMe.common.constants.EwmNames;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.EventMapper;
import ru.practicum.exploreWithMe.event.EventViewsAndRequestsHandler;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.enums.EventSortValue;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.request.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final RequestRepository requestRepository;
    private final StatisticsClient statisticsClient;

    @Override
    public EventFullDto get(Long eventId, HttpServletRequest httpServletRequest) {
        statsCollect(httpServletRequest);
        Event event = checkEventExistence(eventId);

        if (event.getState() != EventState.PUBLISHED) {
            log.error("Опубликованное событие по ID {} не найдено", eventId);
            throw new ObjectNotFoundException("Событие", eventId);
        }

        EventFullDto eventFullDto = eventMapper.transformEventToEventFullDto(event);
        EventViewsAndRequestsHandler.addViewsAndConfirmedRequests(List.of(eventFullDto), statisticsClient,
                requestRepository);
        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getAllByPublicFilters(String text, List<Long> categoryIds, Boolean paid,
                                                     LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                     Boolean onlyAvailable, EventSortValue sort, Integer from,
                                                     Integer size, HttpServletRequest httpServletRequest) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now().minusYears(1);
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }
        if (rangeEnd.isBefore(rangeStart)) {
            log.error("Некорректные даты в запросе. start: {}, end: {}", rangeStart, rangeEnd);
            throw new IllegalArgumentException(String.format("Некорректные даты в запросе. start: %s, end: %s",
                    rangeStart, rangeEnd));
        }
        statsCollect(httpServletRequest);

        List<EventFullDto> eventFullDtos = eventRepository.findAllByPublicFilters(text, categoryIds, paid, rangeStart,
                        rangeEnd, sort, from, size)
                .stream().map(eventMapper::transformEventToEventFullDto).collect(Collectors.toList());

        if (onlyAvailable) {
            eventFullDtos = eventFullDtos.stream().filter(
                    eventFullDto -> eventFullDto.getParticipantLimit() <= eventFullDto.getConfirmedRequests())
                    .collect(Collectors.toList());
        }
        if (sort == EventSortValue.VIEWS) {
            eventFullDtos.sort(Comparator.comparingLong(EventFullDto::getViews));
        }

        EventViewsAndRequestsHandler.addViewsAndConfirmedRequests(eventFullDtos, statisticsClient, requestRepository);
        return eventFullDtos.stream().map(eventMapper::transformEventFullDtoToEventShortDto)
                .collect(Collectors.toList());
    }

    private Event checkEventExistence(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Ошибка получения события по ID {}, такого ID не существует!", eventId);
            throw new ObjectNotFoundException("Событие", eventId);
        });
    }

    private void statsCollect(HttpServletRequest httpServletRequest) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();

        endpointHitDto.setApp(EwmNames.app);
        endpointHitDto.setUri(httpServletRequest.getRequestURI());
        endpointHitDto.setIp(httpServletRequest.getRemoteAddr());
        endpointHitDto.setTimestamp(LocalDateTime.now());

        statisticsClient.createEndpointHit(endpointHitDto);
    }
}
