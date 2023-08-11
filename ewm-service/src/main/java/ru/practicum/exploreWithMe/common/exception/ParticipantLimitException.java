package ru.practicum.exploreWithMe.common.exception;

public class ParticipantLimitException extends RuntimeException {
    public ParticipantLimitException(String message) {
        super(message);
    }
}
