package com.BmoGlitchCode.supplemint.application.usecase.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;

/**
 * Exception thrown when a supplement cannot be found.
 */
public class SupplementNotFoundException extends RuntimeException {

    private final SupplementId supplementId;

    public SupplementNotFoundException(SupplementId supplementId) {
        super("Supplement not found with ID: " + supplementId.getValue());
        this.supplementId = supplementId;
    }

    public SupplementId getSupplementId() {
        return supplementId;
    }
}
