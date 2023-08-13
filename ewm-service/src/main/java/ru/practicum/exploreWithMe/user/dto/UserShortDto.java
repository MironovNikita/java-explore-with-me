package ru.practicum.exploreWithMe.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserShortDto {
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;
}
