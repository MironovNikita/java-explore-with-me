package ru.practicum.exploreWithMe.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.category.service.CategoryService;
import ru.practicum.exploreWithMe.common.pagination.Pagination;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable(name = "catId") Long categoryId) {
        log.info("PublicCategoryController: Запрос на получение категории по ID {}", categoryId);
        return categoryService.getById(categoryId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(@PositiveOrZero(message = "Минимальное значение индекса: 0")
                                    @RequestParam(value = "from", defaultValue = "0") Integer from,
                                    @Positive(message = "Минимальное количество элементов: 1")
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("PublicCategoryController: Получение списка всех категорий с параметрами from: {} и size: {}", from,
                size);
        return categoryService.getAll(Pagination.splitByPages(from, size));
    }

}
