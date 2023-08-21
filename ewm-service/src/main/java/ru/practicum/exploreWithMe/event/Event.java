package ru.practicum.exploreWithMe.event;

import lombok.*;
import ru.practicum.exploreWithMe.category.Category;
import ru.practicum.exploreWithMe.comment.Comment;
import ru.practicum.exploreWithMe.common.validation.Create;
import ru.practicum.exploreWithMe.common.validation.Update;
import ru.practicum.exploreWithMe.event.enums.EventState;
import ru.practicum.exploreWithMe.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = Create.class, message = "Краткое описание события не может быть пустым")
    @Size(groups = {Create.class, Update.class}, max = 3000,
            message = "Максимальный размер краткого описания - 3000 символов")
    private String annotation;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(groups = Create.class, message = "Категория события не может быть пустой")
    private Category category;

    @JoinColumn(name = "initiator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(groups = Create.class, message = "Инициатор события не может быть пустой")
    private User initiator;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @NotBlank(groups = Create.class, message = "Описание события не может быть пустым")
    @Size(groups = {Create.class, Update.class}, max = 7000, message = "Максимальный размер описания - 7000 символов")
    private String description;

    @NotNull(groups = Create.class, message = "Событие не может быть создано без даты")
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @NotNull(groups = Create.class, message = "Широта не может быть пустой")
    private Float latitude;

    @NotNull(groups = Create.class, message = "Долгота не может быть пустой")
    private Float longitude;

    @NotNull(groups = Create.class, message = "Информация о необходимости оплаты не может быть пустой")
    private Boolean paid;

    @Column(name = "participant_limit")
    @PositiveOrZero(groups = {Create.class, Update.class},
            message = "Количество участников события не может быть отрицательным")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Size(groups = {Create.class, Update.class}, max = 25, message = "Количество символов события - 25") // Под вопросом
    private EventState state;

    @NotBlank(groups = Create.class, message = "Заголовок события не может быть пустой")
    @Size(groups = {Create.class, Update.class}, max = 200,
            message = "Максимальный размер заголовка события - 200 символов")
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private List<Comment> comments;
}
