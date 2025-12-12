package com.BmoGlitchCode.supplemint.application.dto.response.user;

import java.time.Instant;

/**
 * Response DTO for user data.
 * Excludes sensitive information like password hash.
 */
public record UserResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        String fullName,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {
}
