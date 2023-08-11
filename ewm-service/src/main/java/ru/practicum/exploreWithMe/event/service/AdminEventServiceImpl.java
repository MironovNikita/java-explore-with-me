package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.StatisticsClient;
import ru.practicum.exploreWithMe.category.Category;
import ru.practicum.exploreWithMe.category.CategoryRepository;
import ru.practicum.exploreWithMe.common.exception.EventStateException;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;
import ru.practicum.exploreWithMe.common.exception.RequestParametersException;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.EventMapper;
import ru.practicum.exploreWithMe.event.EventViewsAndRequestsHandler;
import ru.practicum.exploreWithMe.event.enums.EventStateAdmin;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.event.dto.AdminEventUpdateDto;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.request.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final StatisticsClient statisticsClient;

    @Override
    @Transactional
    public EventFullDto redactEvent(Long eventId, AdminEventUpdateDto eventUpdateDto) {
        Event event = checkEventExistence(eventId);

        if (eventUpdateDto.getAnnotation() != null) {
            event.setAnnotation(eventUpdateDto.getAnnotation());
        }
        if (eventUpdateDto.getCategory() != null) {
            Category category = checkCategoryExistence(eventUpdateDto.getCategory());
            event.setCategory(category);
        }
        if (eventUpdateDto.getDescription() != null) {
            event.setDescription(eventUpdateDto.getDescription());
        }
        if (eventUpdateDto.getEventDate() != null) {
            if (eventUpdateDto.getEventDate().isBefore(LocalDateTime.now())) {
                log.error("Некорректная дата события! Переданная дата события {} не может быть в прошлом!",
                        eventUpdateDto.getEventDate());
                throw new RequestParametersException(
                        String.format("Некорректная дата события! Переданная дата события (%s) не может быть в прошлом!",
                                eventUpdateDto.getEventDate()));
            }
            event.setEventDate(eventUpdateDto.getEventDate());
        }
        if (eventUpdateDto.getPaid() != null) {
            event.setPaid(eventUpdateDto.getPaid());
        }
        if (eventUpdateDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        if (eventUpdateDto.getRequestModeration() != null) {
            event.setRequestModeration(eventUpdateDto.getRequestModeration());
        }
        if (eventUpdateDto.getTitle() != null) {
            event.setTitle(eventUpdateDto.getTitle());
        }
        if (eventUpdateDto.getStateAction() != null) {
            if (event.getState() != EventState.PENDING) {
                log.error("Событие с ID {} не находится в статусе на рассмотрении", eventId);
                throw new EventStateException(String.format("Событие с ID %d не находится в статусе на рассмотрении",
                        eventId));
            }

            EventState eventState = eventUpdateDto.getStateAction() == EventStateAdmin.PUBLISH_EVENT ?
                    EventState.PUBLISHED : EventState.CANCELED;
            event.setState(eventState);
            event.setPublishedOn(LocalDateTime.now());
        }

        EventFullDto eventFullDto = eventMapper.transformEventToEventFullDto(event);
        EventViewsAndRequestsHandler.addViewsAndConfirmedRequests(List.of(eventFullDto), statisticsClient,
                requestRepository);

        return eventFullDto;
    }

    @Override
    public List<EventFullDto> getAllEventsByAdminFilters(List<Long> users, List<EventState> states,
                                                    List<Long> categories, LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd, Integer from, Integer size) {
        List<EventFullDto> eventFullDtos = eventRepository.findAllByAdminFilters(users, states, categories, rangeStart,
                rangeEnd, from, size)
                .stream().map(eventMapper::transformEventToEventFullDto).collect(Collectors.toList());

        EventViewsAndRequestsHandler.addViewsAndConfirmedRequests(eventFullDtos, statisticsClient, requestRepository);

        return eventFullDtos;
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
