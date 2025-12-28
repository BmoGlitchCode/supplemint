package com.BmoGlitchCode.supplemint.application.dto.request.stack;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddStackItemRequest(
        @NotNull(message = "User ID cannot be null") UUID userId,

        @NotNull(message = "Supplement ID cannot be null") UUID supplementId,

        int sortOrder,

        String notes) {
}
