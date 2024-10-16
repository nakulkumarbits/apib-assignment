package com.bitspilani.fooddeliverysystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthRequest {

    @Schema(description = "The username of the user trying to log in", example = "john_doe")
    private String username;

    @Schema(description = "The password of the user trying to log in")
    private String password;
}
