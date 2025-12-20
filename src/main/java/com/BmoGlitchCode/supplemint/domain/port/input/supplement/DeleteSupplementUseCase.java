package com.BmoGlitchCode.supplemint.domain.port.input.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

public interface DeleteSupplementUseCase {

    void delete(DeleteSupplementCommand command);

    record DeleteSupplementCommand(
            UserId userId, // Ownership check
            SupplementId supplementId) {
    }
}
