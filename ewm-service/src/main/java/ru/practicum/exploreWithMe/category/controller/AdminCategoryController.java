package ru.practicum.exploreWithMe.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.category.service.CategoryService;
import ru.practicum.exploreWithMe.common.validation.Create;
import ru.practicum.exploreWithMe.common.validation.Update;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Validated(Create.class) @RequestBody CategoryDto categoryDto) {
        log.info("AdminCategoryController: Запрос на создание категории с названием {}", categoryDto.getName());
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto update(@PathVariable(name = "catId") Long categoryId,
                              @Validated(Update.class) @RequestBody CategoryDto categoryDto) {
        log.info("AdminCategoryController: Запрос на обновление категории с под ID {}", categoryId);
        return categoryService.update(categoryId, categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "catId") Long categoryId) {
        log.info("AdminCategoryController: Запрос на удаление категории с ID {}", categoryId);
        categoryService.delete(categoryId);
    }
}
