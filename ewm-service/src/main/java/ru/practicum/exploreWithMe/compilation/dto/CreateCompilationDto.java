package ru.practicum.exploreWithMe.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompilationDto {
    private Long id;
    private boolean pinned;
    @NotBlank(message = "При создании подборки событий заголовок не может быть пустым")
    @Size(min = 1, max = 50, message = "Заголовок подборки может минимум содержать 1 символ, максимум - 50 символов")
    private String title;
    private List<Long> events;
}
