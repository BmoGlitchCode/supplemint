package com.BmoGlitchCode.supplemint.application.usecase.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Exception thrown when a user attempts to access a supplement they do not own.
 */
public class SupplementAccessDeniedException extends RuntimeException {

    private final SupplementId supplementId;
    private final UserId userId;

    public SupplementAccessDeniedException(SupplementId supplementId, UserId userId) {
        super("User does not have access to supplement with ID: " + supplementId.getValue());
        this.supplementId = supplementId;
        this.userId = userId;
    }

    public SupplementId getSupplementId() {
        return supplementId;
    }

    public UserId getUserId() {
        return userId;
    }
}
