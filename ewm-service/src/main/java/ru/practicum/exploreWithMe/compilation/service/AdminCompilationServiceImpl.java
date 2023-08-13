package ru.practicum.exploreWithMe.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;
import ru.practicum.exploreWithMe.compilation.Compilation;
import ru.practicum.exploreWithMe.compilation.CompilationMapper;
import ru.practicum.exploreWithMe.compilation.CompilationRepository;
import ru.practicum.exploreWithMe.compilation.dto.CreateCompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.FullCompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.UpdateCompilationDto;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.event.repository.EventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    public FullCompilationDto create(CreateCompilationDto compilationDto) {
        Compilation compilation = compilationMapper.transformCreateCompilationDtoToCompilation(compilationDto);

        if (compilationDto.getEvents() != null) {
            Set<Event> events = eventRepository.findAllByIdIn(compilationDto.getEvents());
            compilation.setEvents(events);
        }

        return compilationMapper.transformCompilationToFullCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public FullCompilationDto update(Long compilationId, UpdateCompilationDto compilationDto) {
        Compilation compilation = checkCompilationExistence(compilationId);
        List<Long> eventIds = compilationDto.getEvents();

        if (eventIds != null) {
            Set<Event> eventList = eventRepository.findAllByIdIn(compilationDto.getEvents());
            compilation.setEvents(new HashSet<>(eventList));
        }
        if (compilationDto.getPinned() != null) {
            compilation.setPinned(compilationDto.getPinned());
        }
        if (compilationDto.getTitle() != null && !compilationDto.getTitle().isBlank()) {
            compilation.setTitle(compilationDto.getTitle());
        }

        return compilationMapper.transformCompilationToFullCompilationDto(compilation);
    }

    @Override
    public void delete(Long compilationId) {
        checkCompilationExistence(compilationId);
        compilationRepository.deleteById(compilationId);
    }

    private Compilation checkCompilationExistence(Long compilationId) {
        return compilationRepository.findById(compilationId).orElseThrow(() -> {
            log.error("Ошибка получения подборки событий по ID {}, такого ID не существует!", compilationId);
            throw new ObjectNotFoundException("Категория", compilationId);
        });
    }
}
