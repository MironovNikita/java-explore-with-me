package ru.practicum.exploreWithMe.common.exception;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String object, String name) {
        super(String.format("%s с названием: %s уже существует!", object, name));
    }
}
