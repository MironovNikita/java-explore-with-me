package ru.practicum.exploreWithMe.common.exception;

public class EventStartAfterHoursException extends RuntimeException {
    public EventStartAfterHoursException(String message) {
        super(message);
    }
}
