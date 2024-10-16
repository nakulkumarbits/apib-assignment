package com.bitspilani.fooddeliverysystem.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {

    private String message;
    private List<FieldError> errors;

    @Data
    @AllArgsConstructor
    public static class FieldError {

        private String field;
        private String message;
    }
}