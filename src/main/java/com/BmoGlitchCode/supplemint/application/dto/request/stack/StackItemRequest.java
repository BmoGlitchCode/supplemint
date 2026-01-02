package com.BmoGlitchCode.supplemint.application.dto.request.stack;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record StackItemRequest(
        @NotNull(message = "Supplement ID cannot be null") UUID supplementId,

        @PositiveOrZero(message = "Sort order cannot be negative") Integer sortOrder) {
}
