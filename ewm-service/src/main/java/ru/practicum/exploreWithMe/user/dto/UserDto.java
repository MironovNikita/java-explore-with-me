package ru.practicum.exploreWithMe.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Email пользователя не может быть пустым")
    @Size(min = 6, max = 254, message = "Минимальный размер email - 6 символов, максимальный - 254 символа")
    @Email(message = "Введённый email некорректен")
    private String email;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 250, message = "Минимальный размер имени - 2 символа, максимальный - 50 символов")
    private String name;
}
