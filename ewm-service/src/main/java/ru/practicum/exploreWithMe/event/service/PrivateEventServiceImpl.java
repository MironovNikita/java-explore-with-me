package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.StatisticsClient;
import ru.practicum.exploreWithMe.category.Category;
import ru.practicum.exploreWithMe.category.CategoryRepository;
import ru.practicum.exploreWithMe.common.exception.*;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.EventMapper;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.event.EventViewsAndRequestsHandler;
import ru.practicum.exploreWithMe.event.dto.*;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.event.enums.EventStateUser;
import ru.practicum.exploreWithMe.request.Request;
import ru.practicum.exploreWithMe.request.RequestMapper;
import ru.practicum.exploreWithMe.request.RequestRepository;
import ru.practicum.exploreWithMe.request.RequestStatus;
import ru.practicum.exploreWithMe.request.dto.RequestDto;
import ru.practicum.exploreWithMe.user.User;
import ru.practicum.exploreWithMe.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final StatisticsClient statisticsClient;


    @Override
    @Transactional
    public EventFullDto create(Long userId, CreateEventDto eventDto) {
        User user = checkUserExistence(userId);
        Category category = checkCategoryExistence(eventDto.getCategory());

        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(3))) {
            log.error("Невозможно обновить время события на {}. Оно не удовлетворяет требованиям",
                    eventDto.getEventDate());
            throw new EventStartAfterHoursException(
                    String.format("Невозможно обновить время события на %s. Оно не удовлетворяет требованиям",
                            eventDto.getEventDate()));
        }

        Event event = eventMapper.transformCreateEventDtoToEvent(eventDto);

        event.setCategory(category);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);

        return eventMapper.transformEventToEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto update(Long userId, Long eventId, UpdateEventDtoByUser dtoByUser) {
        checkUserExistence(userId);
        Event event = checkEventExistence(eventId);

        if (!event.getInitiator().getId().equals(userId)) {
            log.error("Пользователь с ID {} не является создателем события с ID {}", userId, eventId);
            throw new InvalidUserException(userId, event.getInitiator().getId());
        }

        if (event.getState().equals(EventState.PUBLISHED)) {
            log.error("Событие с ID {} уже опубликовано!", eventId);
            throw new EventStateException(String.format("Событие с ID %d уже опубликовано!", eventId));
        }

        if (dtoByUser.getAnnotation() != null) {
            event.setAnnotation(dtoByUser.getAnnotation());
        }

        if (dtoByUser.getCategory() != null) {
            Category updateCategory = checkCategoryExistence(dtoByUser.getCategory());
            event.setCategory(updateCategory);
        }

        if (dtoByUser.getDescription() != null) {
            event.setDescription(dtoByUser.getDescription());
        }

        if (dtoByUser.getEventDate() != null) {
            LocalDateTime eventTime = dtoByUser.getEventDate();
            if (eventTime.isBefore(LocalDateTime.now()) || eventTime.isBefore(event.getPublishedOn())) {
                log.error("Невозможно обновить время события на {}. Оно не удовлетворяет требованиям",
                        dtoByUser.getEventDate());
                throw new EventStartAfterHoursException(
                        String.format("Невозможно обновить время события на %s. Оно не удовлетворяет требованиям",
                                dtoByUser.getEventDate()));
            }
            event.setEventDate(dtoByUser.getEventDate());
        }

        if (dtoByUser.getLocation() != null) {
            event.setLatitude(dtoByUser.getLocation().getLat());
            event.setLongitude(dtoByUser.getLocation().getLon());
        }

        if (dtoByUser.getPaid() != null) {
            event.setPaid(dtoByUser.getPaid());
        }

        if (dtoByUser.getParticipantLimit() != null) {
            event.setParticipantLimit(dtoByUser.getParticipantLimit());
        }

        if (dtoByUser.getRequestModeration() != null) {
            event.setRequestModeration(dtoByUser.getRequestModeration());
        }

        if (dtoByUser.getTitle() != null) {
            event.setTitle(dtoByUser.getTitle());
        }

        if (dtoByUser.getStateAction() != null) {
            EventState eventState = dtoByUser.getStateAction() == EventStateUser.SEND_TO_REVIEW ? EventState.PENDING :
                    EventState.CANCELED;
            event.setState(eventState);
        }

        EventFullDto eventFullDto = eventMapper.transformEventToEventFullDto(event);
        EventViewsAndRequestsHandler.addViewsAndConfirmedRequests(List.of(eventFullDto), statisticsClient,
                requestRepository);
        return eventFullDto;
    }

    @Override
    public EventFullDto get(Long userId, Long eventId) {
        checkUserExistence(userId);
        Event event = checkEventExistence(eventId);

        if (!event.getInitiator().getId().equals(userId)) {
            log.error("Пользователь с ID {} не является создателем события с ID {}", userId, eventId);
            throw new InvalidUserException(userId, event.getInitiator().getId());
        }

        EventFullDto eventFullDto = eventMapper.transformEventToEventFullDto(event);
        EventViewsAndRequestsHandler.addViewsAndConfirmedRequests(List.of(eventFullDto), statisticsClient,
                requestRepository);
        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getAllEventsOfUser(Long userId, Pageable pageable) {
        List<EventFullDto> eventFullDtos = eventRepository.findAllByInitiatorId(userId, pageable).stream()
                .map(eventMapper::transformEventToEventFullDto).collect(Collectors.toList());
        EventViewsAndRequestsHandler.addViewsAndConfirmedRequests(eventFullDtos, statisticsClient, requestRepository);

        return eventFullDtos.stream().map(eventMapper::transformEventFullDtoToEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResultDto updateRequestsOfEvent(Long userId, Long eventId,
                                                                   EventRequestStatusUpdateRequestDto updateRequestDto) {
        checkUserExistence(userId);
        Event event = checkEventExistence(eventId);
        long amountConfirmedEventRequests = requestRepository.findQuantityOfEventConfirmedRequests(eventId);

        if (event.getParticipantLimit() != 0L && amountConfirmedEventRequests == event.getParticipantLimit()) {
            log.error("Превышен лимит участников события с ID {}", eventId);
            throw new ParticipantLimitException("Превышен лимит участников!");
        }

        List<Request> requests = requestRepository.findRequestsForUpdate(eventId, userId,
                updateRequestDto.getRequestIds());

        if (updateRequestDto.getStatus().equals(RequestStatus.REJECTED)) {
            requests.forEach(request -> request.setStatus(RequestStatus.REJECTED));

            return new EventRequestStatusUpdateResultDto(Collections.emptyList(),
                    requests.stream().map(requestMapper::transformRequestToRequestDto).collect(Collectors.toList()));
        }

        EventRequestStatusUpdateResultDto resultDto = new EventRequestStatusUpdateResultDto(Collections.emptyList(),
                Collections.emptyList());

        requests.forEach(request -> {
            if (amountConfirmedEventRequests < event.getParticipantLimit()) {
                request.setStatus(RequestStatus.CONFIRMED);
                List<RequestDto> confirmedDtos = new ArrayList<>(resultDto.getConfirmedRequests());
                confirmedDtos.add(requestMapper.transformRequestToRequestDto(request));
                resultDto.setConfirmedRequests(confirmedDtos);
            } else {
                request.setStatus(RequestStatus.REJECTED);
                List<RequestDto> rejectedDtos = new ArrayList<>(resultDto.getRejectedRequests());
                rejectedDtos.add(requestMapper.transformRequestToRequestDto(request));
                resultDto.setRejectedRequests(rejectedDtos);
            }
        });
        return resultDto;
    }

    @Override
    public List<RequestDto> getRequestsOfEvent(Long userId, Long eventId) {
        checkUserExistence(userId);
        checkEventExistence(eventId);

        return requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId).stream()
                .map(requestMapper::transformRequestToRequestDto).collect(Collectors.toList());
    }

    private User checkUserExistence(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("Ошибка получения пользователя по ID {}, такого ID не существует!", userId);
            throw new ObjectNotFoundException("Пользователь", userId);
        });
    }

    private Category checkCategoryExistence(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("Ошибка получения категории по ID {}, такого ID не существует!", categoryId);
            throw new ObjectNotFoundException("Категория", categoryId);
        });
    }

    private Event checkEventExistence(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Ошибка получения события по ID {}, такого ID не существует!", eventId);
            throw new ObjectNotFoundException("Событие", eventId);
        });
    }
}
