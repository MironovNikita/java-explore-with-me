package ru.practicum.exploreWithMe.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.common.pagination.Pagination;
import ru.practicum.exploreWithMe.compilation.dto.FullCompilationDto;
import ru.practicum.exploreWithMe.compilation.service.PublicCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final PublicCompilationService publicCompilationService;

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public FullCompilationDto get(@PathVariable(name = "compId") Long compilationId) {
        log.info("PublicCompilationController: Запрос на получение подборки событий по ID {}", compilationId);
        return publicCompilationService.get(compilationId);
    }

    @GetMapping
    public List<FullCompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                           @PositiveOrZero(message = "Минимальное значение индекса: 0")
                                           @RequestParam(value = "from", defaultValue = "0") Integer from,
                                           @Positive(message = "Минимальное количество элементов: 1")
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("PublicCompilationController: Запрос на получение подборок событий по параметрам:\n" +
                "- закреплённые: {};\n" +
                "- from: {};\n" +
                "- size: {}.", pinned, from, size);
        return publicCompilationService.getAll(pinned, Pagination.splitByPages(from, size));
    }
}
