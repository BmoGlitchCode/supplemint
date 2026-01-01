package com.BmoGlitchCode.supplemint.application.dto.request.stack;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddStackItemRequest(
        @NotNull(message = "User ID cannot be null") UUID userId,

        @NotNull(message = "Supplement ID cannot be null") UUID supplementId,

        @PositiveOrZero(message = "Sort order cannot be negative") int sortOrder,

        @Size(max = 500, message = "Notes must be at most 500 characters") String notes) {
}
