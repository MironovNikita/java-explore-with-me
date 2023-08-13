package ru.practicum.exploreWithMe.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.common.exception.*;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.request.Request;
import ru.practicum.exploreWithMe.request.RequestMapper;
import ru.practicum.exploreWithMe.request.RequestRepository;
import ru.practicum.exploreWithMe.request.RequestStatus;
import ru.practicum.exploreWithMe.request.dto.RequestDto;
import ru.practicum.exploreWithMe.user.User;
import ru.practicum.exploreWithMe.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public RequestDto create(Long userId, Long eventId) {
        User user = checkUserExistence(userId);
        Event event = checkEventExistence(eventId);

        requestRepository.findByRequesterIdAndEventId(userId, eventId).ifPresent(
                request -> {
                    throw new ObjectAlreadyExistsException("Запрос",
                            String.format("участие в событии с ID %d", eventId));
                }
        );

        if (event.getState() != EventState.PUBLISHED) {
            log.error("Событие c ID {} не опубликовано! Подать заявку невозможно", eventId);
            throw new EventStateException(String.format("Событие с ID %d не опубликовано! Подать заявку невозможно",
                    eventId));
        }

        if (event.getInitiator().getId().equals(userId)) {
            log.error("Нельзя подать заявку в собственно созданное событие (userId: {}, initiatorId: {})",
                    userId, event.getInitiator().getId());
            throw new SelfEventRequestException(
                    String.format("Нельзя подать заявку в собственно созданное событие (userId: %d, initiatorId: %d",
                            userId, event.getInitiator().getId()));
        }

        long confirmedRequests = requestRepository.findQuantityOfEventConfirmedRequests(eventId);

        if (event.getParticipantLimit() != 0 && confirmedRequests == event.getParticipantLimit()) {
            throw new ParticipantLimitException(
                    String.format("Невозможно подать заявку на участие в событии с ID %d. Лимит участников достигнут",
                            eventId));
        }

        RequestStatus requestStatus = RequestStatus.PENDING;

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            requestStatus = RequestStatus.CONFIRMED;
        }

        Request request = Request.builder().event(event).requester(user)
                .created(LocalDateTime.now()).status(requestStatus).build();
        return requestMapper.transformRequestToRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestDto cancel(Long userId, Long requestId) {
        checkUserExistence(userId);
        Request request = checkRequestExistence(requestId);

        if (!request.getRequester().getId().equals(userId)) {
            throw new ObjectNotFoundException("Запрос", requestId);
        }
        request.setStatus(RequestStatus.CANCELED);

        return requestMapper.transformRequestToRequestDto(request);
    }

    @Override
    public List<RequestDto> getAll(Long userId) {
        checkUserExistence(userId);

        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::transformRequestToRequestDto).collect(Collectors.toList());
    }

    private Event checkEventExistence(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Ошибка получения события по ID {}, такого ID не существует!", eventId);
            throw new ObjectNotFoundException("Событие", eventId);
        });
    }

    private User checkUserExistence(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("Ошибка получения пользователя по ID {}, такого ID не существует!", userId);
            throw new ObjectNotFoundException("Пользователь", userId);
        });
    }

    private Request checkRequestExistence(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> {
            log.error("Ошибка получения запроса по ID {}, такого ID не существует!", requestId);
            throw new ObjectNotFoundException("Запрос", requestId);
        });
    }
}
