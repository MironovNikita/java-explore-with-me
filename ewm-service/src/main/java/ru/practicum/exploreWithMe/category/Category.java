package ru.practicum.exploreWithMe.category;

import lombok.*;
import ru.practicum.exploreWithMe.common.validation.Create;
import ru.practicum.exploreWithMe.common.validation.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(groups = {Create.class, Update.class}, message = "Название категории не может быть пустым")
    @Size(groups = {Create.class, Update.class}, max = 50,
            message = "Максимальный размер названия категории - 50 символов")
    private String name;
}
