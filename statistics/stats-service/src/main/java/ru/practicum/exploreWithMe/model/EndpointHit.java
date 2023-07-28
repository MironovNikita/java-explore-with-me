package ru.practicum.exploreWithMe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.constant.EwmConstants;
import ru.practicum.exploreWithMe.validation.Create;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "endpointhits")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(groups = Create.class, message = "Идентификатор сервиса не может быть пустым")
    @Size(groups = Create.class, max = 100, message = "Максимальный размер идентификатора сервиса 100 символов")
    private String app;
    @NotBlank(groups = Create.class, message = "URI, для которого был осуществлен запрос, не может быть пустым")
    private String uri;
    @NotBlank(groups = Create.class, message = "IP пользователя не может быть пустым")
    @Size(groups = Create.class, max = 20, message = "IP пользователя не может быть более 20 символов")
    private String ip;
    @NotNull(groups = Create.class, message = "Время запроса не может быть пустым")
    @JsonFormat(pattern = EwmConstants.DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
