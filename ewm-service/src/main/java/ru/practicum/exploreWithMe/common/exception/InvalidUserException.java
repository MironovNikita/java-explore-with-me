package ru.practicum.exploreWithMe.common.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(Long userId, Long initiatorId) {
        super(String.format("Запрос пользователя с ID: %d не выполнен! Создателем является другой пользователь с ID %d",
                userId, initiatorId));
    }
}
