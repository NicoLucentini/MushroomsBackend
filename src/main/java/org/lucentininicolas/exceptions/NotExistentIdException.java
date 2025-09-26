package org.lucentininicolas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public @ResponseStatus(value = HttpStatus.NOT_FOUND)
class NotExistentIdException extends RuntimeException{
    public NotExistentIdException(final String message) {
        super(message);
    }
}