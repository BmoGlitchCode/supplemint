package com.BmoGlitchCode.supplemint.domain.port.input.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

public interface GetSupplementUseCase {

    Supplement get(GetSupplementQuery query);

    record GetSupplementQuery(
            UserId userId, // To ensure user can only get their own supplements
            SupplementId supplementId) {
    }
}
