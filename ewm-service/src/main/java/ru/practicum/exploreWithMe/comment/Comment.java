package ru.practicum.exploreWithMe.comment;

import lombok.*;
import ru.practicum.exploreWithMe.event.Event;
import ru.practicum.exploreWithMe.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Текст комментария не может быть пустым")
    @Size(min = 5, max = 2500, message = "Текст комментария должен составлять от 5 до 2500 символов")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "Отправитель комментария не может отсутствовать")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @NotNull(message = "Комментарий должен быть оставлен к конкретному событию")
    private Event event;

    @Column(name = "created_on")
    @NotNull(message = "Дата создания комментария не должна отсутствовать")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
}
