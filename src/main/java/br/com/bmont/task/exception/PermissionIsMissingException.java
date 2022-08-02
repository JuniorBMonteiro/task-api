package br.com.bmont.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionIsMissingException extends RuntimeException{
    public PermissionIsMissingException(String message) {
        super(message);
    }
}
