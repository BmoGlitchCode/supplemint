package com.BmoGlitchCode.supplemint.application.dto.request.supplement;

import java.math.BigDecimal;
import java.util.UUID;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateSupplementRequest(
                @NotNull(message = "User ID cannot be null") UUID userId,

                @NotBlank(message = "Name cannot be blank")
                @Size(max = 100, message = "Name must be at most 100 characters") String name,

                @Size(max = 500, message = "Description must be at most 500 characters") String description,

                @Size(max = 100, message = "Brand must be at most 100 characters") String brand,

                DosageType dosageType,

                @Positive(message = "Dosage amount must be positive") BigDecimal dosagePerServing,

                DosageUnit dosageUnit,

                @Positive(message = "Serving size must be positive") BigDecimal servingSize,

                @NotNull(message = "Total units cannot be null")
                @Positive(message = "Total units must be positive") BigDecimal totalUnits,

                @PositiveOrZero(message = "Remaining units cannot be negative") BigDecimal remainingUnits,

                @Size(max = 1000, message = "Notes must be at most 1000 characters") String notes) {
}
