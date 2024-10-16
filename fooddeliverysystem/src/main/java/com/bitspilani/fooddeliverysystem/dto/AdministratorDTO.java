package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdministratorDTO {

    @Schema(description = "The username of the user trying to register.", example = "john_doe")
    @NotBlank(message = "username is mandatory")
    @JsonProperty("username")
    private String username;

    @Schema(description = "The password of the user trying to register.")
    @NotBlank(message = "password is mandatory")
    @JsonProperty("password")
    private String password;
}
