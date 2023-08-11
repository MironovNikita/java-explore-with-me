package ru.practicum.exploreWithMe.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ApiError {
    private String message;
    private String reason;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
