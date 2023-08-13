package ru.practicum.exploreWithMe.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.exploreWithMe.compilation.dto.FullCompilationDto;

import java.util.List;

public interface PublicCompilationService {
    FullCompilationDto get(Long compilationId);

    List<FullCompilationDto> getAll(Boolean pinned, Pageable pageable);
}
