package ru.practicum.main.categories.exceptions;

public class CategoryStillUseException extends RuntimeException {

    public CategoryStillUseException(String message) {
        super(message);
    }
}

