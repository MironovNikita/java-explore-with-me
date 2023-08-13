package ru.practicum.exploreWithMe.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.category.Category;
import ru.practicum.exploreWithMe.category.CategoryMapper;
import ru.practicum.exploreWithMe.category.CategoryRepository;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.common.exception.ObjectAlreadyExistsException;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryMapper.transformCategoryDtoToCategory(categoryDto);
        return categoryMapper.transformCategoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto update(Long categoryId, CategoryDto categoryDto) {
        Category category = checkCategoryExistence(categoryId);
        Category checkNameCategory = categoryRepository.findByName(categoryDto.getName());

        if (checkNameCategory != null) {
            if (!checkNameCategory.getId().equals(categoryId)) {
                log.error(String.format("Ошибка! Название категории %s уже существует! Попробуйте изменить название",
                        categoryDto.getName()));
                throw new ObjectAlreadyExistsException("Категория", categoryDto.getName());
            }
        }

        category.setName(categoryDto.getName());
        return categoryMapper.transformCategoryToCategoryDto(category);
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        return categoryMapper.transformCategoryToCategoryDto(checkCategoryExistence(categoryId));
    }

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::transformCategoryToCategoryDto).toList();
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        checkCategoryExistence(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    private Category checkCategoryExistence(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("Ошибка получения пользователя по ID {}, такого ID не существует!", categoryId);
            throw new ObjectNotFoundException("Категория", categoryId);
        });
    }
}
