package ru.practicum.exploreWithMe.compilation.service;

import ru.practicum.exploreWithMe.compilation.dto.CreateCompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.FullCompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.UpdateCompilationDto;

public interface AdminCompilationService {
    FullCompilationDto create(CreateCompilationDto compilationDto);

    FullCompilationDto update(Long compilationId, UpdateCompilationDto compilationDto);

    void delete(Long compilationId);
}
