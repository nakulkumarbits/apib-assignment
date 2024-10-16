package com.bitspilani.fooddeliverysystem.advices;

import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class FoodDeliveryExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationErrorResponse.FieldError> fieldErrors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            fieldErrors.add(new ValidationErrorResponse.FieldError(error.getField(), error.getDefaultMessage()));
        }

        ValidationErrorResponse errorResponse = new ValidationErrorResponse("Validation failed", fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return new ResponseEntity<>("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }
}
