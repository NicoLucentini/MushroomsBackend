package org.lucentininicolas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateIdException extends RuntimeException{
    public DuplicateIdException(final String message) {
        super(message);
    }
}
