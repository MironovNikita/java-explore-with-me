package ru.practicum.exploreWithMe.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.exploreWithMe.common.validation.Create;
import ru.practicum.exploreWithMe.common.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotBlank(groups = {Create.class, Update.class}, message = "Название категории не может быть пустым")
    @Size(groups = {Create.class, Update.class}, max = 50,
            message = "Максимальный размер названия категории - 50 символов")
    private String name;
}
