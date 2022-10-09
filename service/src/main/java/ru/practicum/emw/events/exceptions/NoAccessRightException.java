package ru.practicum.emw.events.exceptions;

public class NoAccessRightException extends RuntimeException {

    public NoAccessRightException(String message) {
        super(message);
    }
}
