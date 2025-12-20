package com.BmoGlitchCode.supplemint.application.dto.response.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record SupplementResponse(
        UUID id,
        String name,
        String description,
        String brand,
        DosageType dosageType,
        BigDecimal defaultDosageAmount,
        DosageUnit defaultDosageUnit,
        String notes,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {
}
