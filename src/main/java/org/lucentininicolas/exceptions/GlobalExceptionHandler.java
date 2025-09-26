package org.lucentininicolas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DuplicateIdException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetails duplicateIdException(DuplicateIdException ex, WebRequest request) {
        return new ErrorDetails(ex.getMessage(), request.getDescription(false));
    }
    @ExceptionHandler(value = {NotExistentIdException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDetails notExistentIdException(DuplicateIdException ex, WebRequest request) {
        return new ErrorDetails(ex.getMessage(), request.getDescription(false));
    }
}
