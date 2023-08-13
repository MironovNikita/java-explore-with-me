package ru.practicum.exploreWithMe.common.exception;

public class SelfEventRequestException extends RuntimeException {
    public SelfEventRequestException(String message) {
        super(message);
    }
}
