package com.bitspilani.fooddeliverysystem.advices;

import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.InvalidRequestException;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodDeliveryExceptionHandlerTest {

    private FoodDeliveryExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exceptionHandler = new FoodDeliveryExceptionHandler();
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        // Mocking MethodArgumentNotValidException
        MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
        BindingResult mockBindingResult = mock(BindingResult.class);

        // Mocking field errors
        FieldError fieldError1 = new FieldError("objectName", "field1", "Field 1 is invalid");
        FieldError fieldError2 = new FieldError("objectName", "field2", "Field 2 is invalid");

        when(mockBindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        when(mockException.getBindingResult()).thenReturn(mockBindingResult);

        // Call the handler
        ResponseEntity<ValidationErrorResponse> responseEntity = exceptionHandler.handleMethodArgumentNotValidException(mockException);

        // Assert response status and body
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ValidationErrorResponse responseBody = responseEntity.getBody();
        assertEquals("Validation failed", responseBody.getMessage());
        assertEquals(2, responseBody.getErrors().size());

        assertEquals("field1", responseBody.getErrors().get(0).getField());
        assertEquals("Field 1 is invalid", responseBody.getErrors().get(0).getMessage());

        assertEquals("field2", responseBody.getErrors().get(1).getField());
        assertEquals("Field 2 is invalid", responseBody.getErrors().get(1).getMessage());
    }

    @Test
    void testHandleHttpRequestMethodNotSupported() {
        // Mock HttpRequestMethodNotSupportedException
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("POST");

        // Call the handler
        ResponseEntity<String> responseEntity = exceptionHandler.handleHttpRequestMethodNotSupported(exception);

        // Assert response status and body
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());
        assertEquals("Method not allowed", responseEntity.getBody());
    }

    @Test
    void testHandleUserNotFoundException() {
        // Mock UserNotFoundException
        UserNotFoundException exception = new UserNotFoundException(FoodDeliveryConstants.USER_NOT_PRESENT);

        // Call the handler
        ResponseEntity<ValidationErrorResponse> responseEntity = exceptionHandler.handleCustomerNotFound(exception);

        // Assert response status and body
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ValidationErrorResponse responseBody = responseEntity.getBody();
        assertEquals(FoodDeliveryConstants.USER_NOT_PRESENT, responseBody.getMessage());
        assertNull(responseBody.getErrors());
    }

    @Test
    void testHandleUsernameMismatchException() {
        // Mock UsernameMismatchException
        UsernameMismatchException exception = new UsernameMismatchException(FoodDeliveryConstants.USERNAME_MISMATCH);

        // Call the handler
        ResponseEntity<ValidationErrorResponse> responseEntity = exceptionHandler.handleUsernameMismatchException(exception);

        // Assert response status and body
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ValidationErrorResponse responseBody = responseEntity.getBody();
        assertEquals(FoodDeliveryConstants.USERNAME_MISMATCH, responseBody.getMessage());
        assertNull(responseBody.getErrors());
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        // Mock exception
        DataIntegrityViolationException exception = new DataIntegrityViolationException("test");

        // Call the handler
        ResponseEntity<ValidationErrorResponse> responseEntity = exceptionHandler.handleDataIntegrityViolationException(
            exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getMessage());
    }

    static Stream<Object[]> exceptionProvider() {
        return Stream.of(
            new Object[]{FoodDeliveryConstants.RESTAURANT_STATUS_ERROR},
            new Object[]{FoodDeliveryConstants.DELIVERY_STATUS_ERROR}
        );
    }

    @ParameterizedTest
    @MethodSource("exceptionProvider")
    void testHandleInvalidRequestException(String message) {
        InvalidRequestException exception = new InvalidRequestException(message);
        ResponseEntity<ValidationErrorResponse> responseEntity = exceptionHandler.handleInvalidRequestException(
            exception);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getMessage());
    }
}
