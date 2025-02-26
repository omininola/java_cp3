package br.com.fiap.apirest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){

        Map<String, String> exeptions = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                exeptions.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return new ResponseEntity<>(exeptions, HttpStatus.BAD_REQUEST);
    }
}
