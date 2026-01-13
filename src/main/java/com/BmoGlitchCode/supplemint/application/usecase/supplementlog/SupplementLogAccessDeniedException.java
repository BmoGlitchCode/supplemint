package com.BmoGlitchCode.supplemint.application.usecase.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Exception thrown when a user attempts to access a supplement log they do not own.
 */
public class SupplementLogAccessDeniedException extends RuntimeException {

    private final SupplementLogId logId;
    private final UserId userId;

    public SupplementLogAccessDeniedException(SupplementLogId logId, UserId userId) {
        super("User does not have access to supplement log with ID: " + logId.getValue());
        this.logId = logId;
        this.userId = userId;
    }

    public SupplementLogId getLogId() {
        return logId;
    }

    public UserId getUserId() {
        return userId;
    }
}
