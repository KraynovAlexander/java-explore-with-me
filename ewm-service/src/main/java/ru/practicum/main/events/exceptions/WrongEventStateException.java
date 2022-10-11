package ru.practicum.main.events.exceptions;

public class WrongEventStateException extends RuntimeException {

    public WrongEventStateException(String message) {
        super(message);
    }
}
