package ru.practicum.exploreWithMe.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestParametersException extends RuntimeException {
    public RequestParametersException(String parameter) {
        super(String.format("Ошибка параметра(-ов) запроса: %s. Проверьте введённые данные в запросе", parameter));
    }
}
