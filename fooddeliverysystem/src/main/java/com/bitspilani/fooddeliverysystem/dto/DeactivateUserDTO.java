package com.bitspilani.fooddeliverysystem.dto;

import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeactivateUserDTO {

    @Schema(description = "The username of the user which should be deactivated.")
    @NotBlank(message = "username is mandatory")
    @JsonProperty("username")
    private String username;

    @Schema(description = "The role of the user which should be deactivated.")
    @JsonProperty("role")
    private UserRole userRole;

    private boolean deactivated;
}
