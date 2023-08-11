package ru.practicum.exploreWithMe.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompilationDto {
    private Boolean pinned;
    @Size(min = 1, max = 50, message = "Заголовок подборки может минимум содержать 1 символ, максимум - 50 символов")
    private String title;
    private List<Long> events;
}
