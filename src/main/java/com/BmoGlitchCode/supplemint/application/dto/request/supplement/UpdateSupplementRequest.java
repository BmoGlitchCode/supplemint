package com.BmoGlitchCode.supplemint.application.dto.request.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateSupplementRequest(
        @NotNull(message = "User ID cannot be null") UUID userId,

        @NotBlank(message = "Name cannot be blank") String name,

        String description,

        String brand,

        DosageType dosageType,

        @Positive(message = "Dosage amount must be positive") BigDecimal defaultDosageAmount,

        DosageUnit defaultDosageUnit,

        String notes) {
}
