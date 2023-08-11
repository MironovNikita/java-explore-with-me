package ru.practicum.exploreWithMe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
                .body(Mono.just(endpointHitDto), EndpointHitDto.class).exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.CREATED)) {
                        return response.bodyToMono(Object.class)
                                .map(body -> ResponseEntity.status(HttpStatus.CREATED).body(body));
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                }).block();
    }

    public ResponseEntity<Object> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        log.info("Выполнение запроса \"/stats\" клиента на получение статистики по endpoint");
        String uriParameters = uris.stream().reduce("", (result, uri) -> result + "&uris=" + uri);
        return webClient.get().uri(uriBuilder -> uriBuilder.path("/stats").queryParam("start", start)
                .queryParam("end", end).query(uriParameters)
                .queryParam("unique", unique).build()).exchangeToMono(
                        response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                return response.bodyToMono(Object.class).map(body -> ResponseEntity.ok().body(body));
                            } else {
                                return response.createException().flatMap(Mono::error);
                            }
                        }).block();
    }
}