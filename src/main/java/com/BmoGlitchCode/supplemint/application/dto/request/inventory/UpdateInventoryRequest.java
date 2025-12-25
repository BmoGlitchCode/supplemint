package com.BmoGlitchCode.supplemint.application.dto.request.inventory;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateInventoryRequest(
        @NotNull(message = "User ID is required") UUID userId,

        @DecimalMin(value = "0.0", message = "Remaining units cannot be negative") BigDecimal remainingUnits,

        String notes) {
}
