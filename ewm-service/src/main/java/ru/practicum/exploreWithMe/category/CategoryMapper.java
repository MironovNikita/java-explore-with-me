package ru.practicum.exploreWithMe.category;

import org.mapstruct.Mapper;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category transformCategoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto transformCategoryToCategoryDto(Category category);
}
