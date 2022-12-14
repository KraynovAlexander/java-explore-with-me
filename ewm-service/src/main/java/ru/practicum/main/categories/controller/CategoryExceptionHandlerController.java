package ru.practicum.main.categories.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.business.helper.Error;

@RestControllerAdvice
public class CategoryExceptionHandlerController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error dataIntegrityViolationExceptionHandler(final DataIntegrityViolationException e) {

        return new Error(e.getMessage());

    }

}
