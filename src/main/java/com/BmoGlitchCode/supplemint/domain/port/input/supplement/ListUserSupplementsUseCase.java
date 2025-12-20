package com.BmoGlitchCode.supplemint.domain.port.input.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;

public interface ListUserSupplementsUseCase {

    List<Supplement> list(ListUserSupplementsQuery query);

    record ListUserSupplementsQuery(
            UserId userId,
            boolean activeOnly // If true, return only non-deleted supplements
    ) {
    }
}
