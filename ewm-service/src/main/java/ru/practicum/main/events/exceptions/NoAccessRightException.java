package ru.practicum.main.events.exceptions;

public class NoAccessRightException extends RuntimeException {

    public NoAccessRightException(String message) {
        super(message);
    }
}
