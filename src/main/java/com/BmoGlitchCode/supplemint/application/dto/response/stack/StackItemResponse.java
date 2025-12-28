package com.BmoGlitchCode.supplemint.application.dto.response.stack;

import java.math.BigDecimal;
import java.util.UUID;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;

public record StackItemResponse(
        UUID supplementId,
        String supplementName,
        String supplementDescription,
        BigDecimal dosagePerServing,
        DosageUnit dosageUnit,
        int sortOrder,
        String notes) {
}
