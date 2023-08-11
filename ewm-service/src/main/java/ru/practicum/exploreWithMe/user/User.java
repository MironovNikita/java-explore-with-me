package ru.practicum.exploreWithMe.user;

import lombok.*;
import ru.practicum.exploreWithMe.common.validation.Create;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(groups = Create.class, message = "Email пользователя не может быть пустым")
    @Size(groups = Create.class, max = 50, message = "Максимальный размер email - 50 символов")
    @Email(groups = Create.class, message = "Введённый email некорректен")
    private String email;
    @NotBlank(groups = Create.class, message = "Имя пользователя не может быть пустым")
    @Size(groups = Create.class, max = 50, message = "Максимальный размер имени - 50 символов")
    private String name;
}
