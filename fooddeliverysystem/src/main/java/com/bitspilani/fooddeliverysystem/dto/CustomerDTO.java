package com.bitspilani.fooddeliverysystem.dto;

import com.bitspilani.fooddeliverysystem.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDTO {

    @Schema(description = "The firstname of the user trying to register.")
    @NotBlank(message = "firstname is mandatory")
    @JsonProperty("firstname")
    private String firstName;

    @Schema(description = "The lastname of the user trying to register.")
    @JsonProperty("lastname")
    private String lastName;

    @Schema(description = "The email of the user trying to register.")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @JsonProperty("email")
    private String email;

    @Schema(description = "The username of the user trying to register.")
    @NotBlank(message = "username is mandatory")
    @JsonProperty("username")
    private String username;

    @Schema(description = "The password of the user trying to register.")
    @NotBlank(message = "password is mandatory")
    @JsonProperty("password")
    private String password;

    @Schema(description = "The mobile no of the user trying to register.")
    @NotBlank(message = "mobile no is mandatory")
    @JsonProperty("mobile_no")
    private String mobileNo;

    @Schema(description = "The payment method of the user trying to register.")
    private PaymentMethod paymentMethod;

    @Schema(description = "UPI id for the user trying to register.")
    private String upiId;

    @Schema(description = "Credit/Debit card info for the user trying to register.")
    private String cardNumber;

    @Schema(description = "The address of the user trying to register.")
    private AddressDTO address;
}
