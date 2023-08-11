package ru.practicum.exploreWithMe.common.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(RequestParametersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError requestParametersExceptionHandler(RequestParametersException exception) {
        return new ApiError(exception.getMessage(), exception.getMessage(), HttpStatus.BAD_REQUEST,
                LocalDateTime.now());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError objectNotFoundExceptionHandler(ObjectNotFoundException exception) {
        return new ApiError(exception.getMessage(), exception.getMessage(), HttpStatus.NOT_FOUND,
                LocalDateTime.now());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataAccessExceptionHandler(DataAccessException exception) {
        return new ApiError(exception.getMessage(), exception.getMessage(), HttpStatus.CONFLICT,
                LocalDateTime.now());
    }

    @ExceptionHandler({EventStateException.class, InvalidUserException.class, ObjectAlreadyExistsException.class,
            ParticipantLimitException.class, SelfEventRequestException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflictParametersHandler(Throwable exception) {
        return new ApiError(exception.getMessage(), exception.getMessage(), HttpStatus.CONFLICT,
                LocalDateTime.now());
    }

    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class,
            EventStartAfterHoursException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationExceptionHandler(Throwable exception) {
        return new ApiError(exception.getMessage(), exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }
}
