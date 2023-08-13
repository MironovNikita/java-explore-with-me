package ru.practicum.exploreWithMe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.mapper.EndpointHitMapper;
import ru.practicum.exploreWithMe.mapper.ViewStatsMapper;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository repository;
    private final EndpointHitMapper mapper;
    private final ViewStatsMapper viewStatsMapper;

    @Override
    @Transactional
    public EndpointHitDto createEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = mapper.transformEndpointHitDtoToEndpointHit(endpointHitDto);
        return mapper.transformEndpointHitToEndpointHitDto(repository.save(endpointHit));
    }

    @Override
    public List<ViewStatsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            log.error("Некорректные даты в запросе. start: {}, end: {}", start, end);
            throw new IllegalArgumentException("Некорректные даты в запросе");
        }
        return (repository.getStatsByUris(start, end, uris, unique))
                .stream().map(viewStatsMapper::transformViewStatsToViewStatsDto).collect(Collectors.toList());
    }
}
