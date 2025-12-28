package com.BmoGlitchCode.supplemint.domain.port.input.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.math.BigDecimal;

public interface CreateSupplementUseCase {

        Supplement create(CreateSupplementCommand command);

        record CreateSupplementCommand(
                        UserId userId,
                        String name,
                        String description,
                        String brand,
                        DosageType dosageType,
                        BigDecimal dosagePerServing,
                        DosageUnit dosageUnit, // Can be null if type implies unit
                        BigDecimal servingSize,
                        BigDecimal totalUnits,
                        String notes) {
        }
}
