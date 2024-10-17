package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> errors;

    @Data
    @AllArgsConstructor
    public static class FieldError {

        private String field;
        private String message;
    }
}