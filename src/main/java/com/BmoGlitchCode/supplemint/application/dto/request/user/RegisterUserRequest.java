package com.BmoGlitchCode.supplemint.application.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for user registration.
 */
public record RegisterUserRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255, message = "Email must be at most 255 characters") String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters") String password,

        @Size(max = 50, message = "First name must be at most 50 characters") String firstName,

        @Size(max = 50, message = "Last name must be at most 50 characters") String lastName) {
}
