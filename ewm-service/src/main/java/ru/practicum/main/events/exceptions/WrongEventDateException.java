package ru.practicum.main.events.exceptions;

public class WrongEventDateException extends RuntimeException {

    public WrongEventDateException(String message) {
        super(message);
    }
}
