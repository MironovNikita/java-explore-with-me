package ru.practicum.exploreWithMe.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.compilation.dto.CreateCompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.FullCompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.UpdateCompilationDto;
import ru.practicum.exploreWithMe.compilation.service.AdminCompilationService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullCompilationDto create(@Validated @RequestBody CreateCompilationDto compilationDto) {
        log.info("AdminCompilationController: Запрос на создание подборки событий с заголовком {}",
                compilationDto.getTitle());
        return adminCompilationService.create(compilationDto);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public FullCompilationDto update(@PathVariable(name = "compId") Long compilationId,
                                 @Validated @RequestBody UpdateCompilationDto compilationDto) {
        log.info("AdminCompilationController: Запрос на обновление подборки событий с ID {}", compilationId);
        return adminCompilationService.update(compilationId, compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "compId") Long compilationId) {
        log.info("AdminCompilationController: Запрос на удаление подборки событий с ID {}", compilationId);
        adminCompilationService.delete(compilationId);
    }
}
