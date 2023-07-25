package ru.practicum.exploreWithMe;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.validation.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EndpointHitDto {
    private Long id;
    @NotBlank(groups = Create.class, message = "Идентификатор сервиса не может быть пустым")
    private String app;
    @NotBlank(groups = Create.class, message = "URI, для которого был осуществлен запрос, не может быть пустым")
    private String uri;
    @NotBlank(groups = Create.class, message = "IP пользователя не может быть пустым")
    private String ip;
    @NotNull(groups = Create.class, message = "Время запроса не может быть пустым")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Timestamp timestamp;
}
