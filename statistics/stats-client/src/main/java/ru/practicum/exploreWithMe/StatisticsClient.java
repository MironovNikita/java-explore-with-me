package ru.practicum.exploreWithMe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class StatisticsClient {
    private final WebClient webClient;

    public StatisticsClient(@Value("${statistics-service-server.url}") String url) {
        webClient = WebClient.builder().baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public void createEndpointHit(EndpointHitDto endpointHitDto) {
        log.info("Выполнение запроса \"/hit\" клиента на создание endpoint");
        webClient.post().uri("/hit")
                .body(Mono.just(endpointHitDto), EndpointHitDto.class).retrieve();
    }

    public Mono<List<ViewStatsDto>> getStatistics() {
        log.info("Выполнение запроса \"/stats\" клиента на получение статистики по endpoint");
        return webClient.get().uri("/stats")
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<ViewStatsDto>>() {});
    }
}