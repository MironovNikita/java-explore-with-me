package ru.practicum.exploreWithMe.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.practicum.exploreWithMe.StatisticsClient;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.constant.EwmConstants;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.request.Request;
import ru.practicum.exploreWithMe.request.RequestRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventViewsAndRequestsHandler {
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(EwmConstants.DATE_FORMAT);
    public static final ObjectMapper objectMapper = new ObjectMapper();

    private static void addViews(List<EventFullDto> events, StatisticsClient statisticsClient) {
        Map<String, EventFullDto> mapEvents = events.stream().collect(Collectors.toMap(event ->
                String.format("/events/%d", event.getId()), event -> event));

        Object statistics = statisticsClient.getStatistics(
                LocalDateTime.parse("2000-01-01 00:00:00", timeFormatter).format(timeFormatter),
                LocalDateTime.parse("4000-01-01 00:00:00", timeFormatter).format(timeFormatter),
                new ArrayList<>(mapEvents.keySet()), true).getBody();

        List<ViewStatsDto> stats = objectMapper.convertValue(statistics, new TypeReference<>() {
        });

        stats.forEach(statistic -> {
            if (mapEvents.containsKey(statistic.getUri())) {
                mapEvents.get(statistic.getUri()).setViews(statistic.getHits());
            }
        });
    }

    private static void addConfirmedRequests(List<EventFullDto> eventDtos, RequestRepository requestRepository) {
        Map<Long, Long> requestsMap = new HashMap<>();

        List<Request> requests = requestRepository.findAllConfirmedRequestsByEventIdIn(
                eventDtos.stream().map(EventFullDto::getId).collect(Collectors.toList())
        );

        requests.forEach(request -> {
            Long eventId = request.getEvent().getId();

            if (!requestsMap.containsKey(eventId)) {
                requestsMap.put(eventId, 0L);
            }

            requestsMap.put(eventId, requestsMap.get(eventId) + 1);
        });

        eventDtos.forEach(eventDto -> {
            if (requestsMap.containsKey(eventDto.getId())) {
                eventDto.setConfirmedRequests(requestsMap.get(eventDto.getId()));
            }
        });
    }

    public static void addViewsAndConfirmedRequests(List<EventFullDto> eventDtos, StatisticsClient statisticsClient,
                                                    RequestRepository requestRepository) {
        addViews(eventDtos, statisticsClient);
        addConfirmedRequests(eventDtos, requestRepository);
    }
}
