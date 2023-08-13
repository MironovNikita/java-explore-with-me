package ru.practicum.exploreWithMe.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.request.dto.RequestDto;
import ru.practicum.exploreWithMe.request.service.RequestService;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("PrivateRequestController: Запрос на создание заявки на участие в событии под ID {} пользователем" +
                "с ID {}", eventId, userId);
        return requestService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public RequestDto cancel(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("PrivateRequestController: Запрос на отмену заявки с ID {} на участие в событии", requestId);
        return requestService.cancel(userId, requestId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> getAll(@PathVariable Long userId) {
        log.info("PrivateRequestController: Запрос на получение информации о заявках пользователя с ID {}", userId);
        return requestService.getAll(userId);
    }
}
