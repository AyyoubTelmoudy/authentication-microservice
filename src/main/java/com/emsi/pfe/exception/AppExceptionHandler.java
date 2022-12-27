package com.emsi.pfe.exception;

import com.emsi.pfe.constant.SecurityConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value=ConstraintViolationException.class)
    public ResponseEntity<Object> HandleConstraintViolationException(ConstraintViolationException ex,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        String propertyName = "";
        for (ConstraintViolation constraintViolation: ex.getConstraintViolations()) {
            for (Path.Node node : constraintViolation.getPropertyPath())
            {propertyName=node.getName();}
            errors.put(propertyName,constraintViolation.getMessage());
        }
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value=EmailNotFoundException.class)
    public ResponseEntity<Object> HandleEmailNotFoundException(EmailNotFoundException ex,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        errors.put(SecurityConstants.EMAIL,SecurityConstants.EMAIL_NOT_FOUND_EXCEPTION_MESSAGE);
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value=IncorrectPassword.class)
    public ResponseEntity<Object> HandleIncorrectPassword(IncorrectPassword ex,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        errors.put(SecurityConstants.ERROR,SecurityConstants.PASSWORD_INCORRECT);
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value=EmailDuplicationException.class)
    public ResponseEntity<Object> HandleEmailDuplicationException(EmailDuplicationException ex,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        errors.put(SecurityConstants.EMAIL,SecurityConstants.EMAIL_DUPLICATION_EXCEPTION_MESSAGE);
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value=RefreshTokenMissingException.class)
    public ResponseEntity<Object> HandleRefreshTokenMissingException(RefreshTokenMissingException ex,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        errors.put(SecurityConstants.ERROR,SecurityConstants.REFRESH_TOKE_MISSING_ERROR_MESSAGE);
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }


}
