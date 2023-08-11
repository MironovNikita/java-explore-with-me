package ru.practicum.exploreWithMe.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;
import ru.practicum.exploreWithMe.compilation.Compilation;
import ru.practicum.exploreWithMe.compilation.CompilationMapper;
import ru.practicum.exploreWithMe.compilation.CompilationRepository;
import ru.practicum.exploreWithMe.compilation.dto.FullCompilationDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public FullCompilationDto get(Long compilationId) {
        Compilation compilation = checkCompilationExistence(compilationId);

        return compilationMapper.transformCompilationToFullCompilationDto(compilation);
    }

    @Override
    public List<FullCompilationDto> getAll(Boolean pinned, Pageable pageable) {
        List<Compilation> compilations;

        if (pinned == null) {
            compilations = compilationRepository.findAll(pageable).toList();
        } else {
            compilations = compilationRepository.findAllByPinned(pinned, pageable);
        }

        return compilations.stream().map(compilationMapper::transformCompilationToFullCompilationDto)
                .collect(Collectors.toList());
    }

    private Compilation checkCompilationExistence(Long compilationId) {
        return compilationRepository.findById(compilationId).orElseThrow(() -> {
            log.error("Ошибка получения подборки событий по ID {}, такого ID не существует!", compilationId);
            throw new ObjectNotFoundException("Категория", compilationId);
        });
    }
}
