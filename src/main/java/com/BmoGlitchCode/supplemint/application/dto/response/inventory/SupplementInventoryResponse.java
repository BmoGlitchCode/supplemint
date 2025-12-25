package com.BmoGlitchCode.supplemint.application.dto.response.inventory;

import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SupplementInventoryResponse(
        UUID id,
        UUID userId,
        UUID supplementId,
        LocalDate purchaseDate,
        BigDecimal totalUnits,
        BigDecimal remainingUnits,
        DosageType unitType,
        Integer labelServings,
        BigDecimal unitsPerServing,
        BigDecimal dosagePerServing,
        DosageUnit dosageUnit,
        LocalDate expirationDate,
        BigDecimal cost,
        String vendor,
        String batchNumber,
        String notes,
        boolean active) {
}
