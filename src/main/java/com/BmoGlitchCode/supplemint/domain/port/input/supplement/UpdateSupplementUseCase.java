package com.BmoGlitchCode.supplemint.domain.port.input.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.math.BigDecimal;

public interface UpdateSupplementUseCase {

    Supplement update(UpdateSupplementCommand command);

    record UpdateSupplementCommand(
            UserId userId, // Required to verify ownership
            SupplementId supplementId,
            String name,
            String description,
            String brand,
            DosageType dosageType,
            BigDecimal defaultDosageAmount,
            DosageUnit defaultDosageUnit,
            String notes) {
    }
}
