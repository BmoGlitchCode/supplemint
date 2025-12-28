package com.BmoGlitchCode.supplemint.application.dto.request.supplement;

import java.math.BigDecimal;
import java.util.UUID;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateSupplementRequest(
                @NotNull(message = "User ID cannot be null") UUID userId,

                @NotBlank(message = "Name cannot be blank") String name,

                String description,

                String brand,

                DosageType dosageType,

                @Positive(message = "Dosage amount must be positive") BigDecimal dosagePerServing,

                DosageUnit dosageUnit,

                @Positive(message = "Serving size must be positive") BigDecimal servingSize,

                String notes,

                @Positive(message = "Remaining units must be positive") BigDecimal remainingUnits) {
}
