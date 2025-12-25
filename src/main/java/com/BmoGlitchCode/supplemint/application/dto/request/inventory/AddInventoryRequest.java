package com.BmoGlitchCode.supplemint.application.dto.request.inventory;

import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record AddInventoryRequest(
        @NotNull(message = "User ID is required") UUID userId,

        @NotNull(message = "Supplement ID is required") UUID supplementId,

        LocalDate purchaseDate,

        @NotNull(message = "Total units are required") @DecimalMin(value = "0.01", message = "Total units must be positive") BigDecimal totalUnits,

        @NotNull(message = "Unit type is required") DosageType unitType,

        Integer labelServings,
        BigDecimal unitsPerServing,
        BigDecimal dosagePerServing,
        DosageUnit dosageUnit,
        LocalDate expirationDate,
        BigDecimal cost,
        String vendor,
        String batchNumber,
        String notes) {
}
