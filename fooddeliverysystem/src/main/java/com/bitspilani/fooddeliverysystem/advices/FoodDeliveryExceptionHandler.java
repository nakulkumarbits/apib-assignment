package com.bitspilani.fooddeliverysystem.advices;

import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.exceptions.InvalidRequestException;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ResponseEntity<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ValidationErrorResponse> handleCustomerNotFound(UserNotFoundException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameMismatchException.class)
    public ResponseEntity<ValidationErrorResponse> handleUsernameMismatchException(UsernameMismatchException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse("Invalid input provided", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse("Invalid input provided", null);
        if (ex.getMessage().contains(FoodDeliveryConstants.RESTAURANT_STATUS_ERROR)) {
            errorResponse.setMessage("Invalid status provided for order.");
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
