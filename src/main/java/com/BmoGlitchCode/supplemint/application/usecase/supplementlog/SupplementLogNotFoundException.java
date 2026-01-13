package com.BmoGlitchCode.supplemint.application.usecase.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;

/**
 * Exception thrown when a supplement log cannot be found.
 */
public class SupplementLogNotFoundException extends RuntimeException {

    private final SupplementLogId logId;

    public SupplementLogNotFoundException(SupplementLogId logId) {
        super("Supplement log not found with ID: " + logId.getValue());
        this.logId = logId;
    }

    public SupplementLogId getLogId() {
        return logId;
    }
}
