package ru.practicum.emw.requests.exceptions;

public class RepeatingRequest extends RuntimeException {

    public RepeatingRequest(String message) {
        super(message);
    }
}
