package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Exception thrown when a user attempts to access a stack they do not own.
 */
public class StackAccessDeniedException extends RuntimeException {

    private final StackId stackId;
    private final UserId userId;

    public StackAccessDeniedException(StackId stackId, UserId userId) {
        super("User does not have access to stack with ID: " + stackId.value());
        this.stackId = stackId;
        this.userId = userId;
    }

    public StackId getStackId() {
        return stackId;
    }

    public UserId getUserId() {
        return userId;
    }
}
