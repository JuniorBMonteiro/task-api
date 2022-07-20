package br.com.bmont.task.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handleEntityNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(createExceptionDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlePermissionIsMissingException(PermissionIsMissingException e){
        return new ResponseEntity<>(createExceptionDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handleEntityAlreadyExistsException(EntityAlreadyExistsException e){
        return new ResponseEntity<>(createExceptionDetails(e), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(createValidationExceptionDetails(ex), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(createExceptionDetails(ex), headers, status);
    }

    private ExceptionDetails createExceptionDetails(Exception e){
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Bad Request Exception, Check the Documentation");
        exceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDetails.setDetails(e.getMessage());
        exceptionDetails.setDeveloperMessage(e.getClass().getName());
        exceptionDetails.setTimestamp(LocalDateTime.now());
        return exceptionDetails;
    }

    private ExceptionDetails createValidationExceptionDetails(MethodArgumentNotValidException e){
        List<FieldError> fieldError = e.getBindingResult().getFieldErrors();
        String fields = fieldError.stream().map(FieldError::getField).collect(Collectors.joining(" ,"));
        String fieldsMessage = fieldError.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(" ,"));

        ValidationExceptionDetails validation = new ValidationExceptionDetails();
        validation.setTitle("Bad Request Exception, Check the Documentation");
        validation.setStatus(HttpStatus.BAD_REQUEST.value());
        validation.setDetails("Check the field(s) error");
        validation.setDeveloperMessage(e.getClass().getName());
        validation.setTimestamp(LocalDateTime.now());
        validation.setFields(fields);
        validation.setFieldsMessage(fieldsMessage);
        return validation;
    }
}
