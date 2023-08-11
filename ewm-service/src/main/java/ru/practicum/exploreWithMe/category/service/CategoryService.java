package ru.practicum.exploreWithMe.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(Long categoryId, CategoryDto categoryDto);

    CategoryDto getById(Long categoryId);

    List<CategoryDto> getAll(Pageable pageable);

    void delete(Long categoryId);
}
