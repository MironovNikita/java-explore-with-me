package ru.practicum.exploreWithMe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.mapper.StatisticsMapper;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository repository;
    private final StatisticsMapper mapper;

    @Override
    @Transactional
    public EndpointHitDto createEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = mapper.endpointHitDtoToEndpointHit(endpointHitDto);
        return mapper.endpointHitToEndpointHitDto(repository.save(endpointHit));
    }

    @Override
    public List<ViewStatsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if ((uris == null || uris.size() == 0) && !unique) {
            return mapper.viewStatsListToListViewStatsDto(repository.findStatisticsAfterStartAndBeforeEnd(start, end));
        } else if (uris == null || uris.size() == 0) {
            return mapper.viewStatsListToListViewStatsDto(
                    repository.findStatisticsAfterStartAndBeforeEndWithUniqueIp(start, end));
        } else if (!unique) {
            return mapper.viewStatsListToListViewStatsDto(
                    repository.findStatisticsAfterStartAndBeforeEndWithUris(start, end, uris));
        }
        return mapper.viewStatsListToListViewStatsDto(
                repository.findStatisticsAfterStartAndBeforeEndWithUrisAndUniqueIp(start, end, uris));
    }
}
