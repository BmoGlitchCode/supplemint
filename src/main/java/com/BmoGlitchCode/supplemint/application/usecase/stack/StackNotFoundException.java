package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;

/**
 * Exception thrown when a stack cannot be found.
 */
public class StackNotFoundException extends RuntimeException {

    private final StackId stackId;

    public StackNotFoundException(StackId stackId) {
        super("Stack not found with ID: " + stackId.value());
        this.stackId = stackId;
    }

    public StackId getStackId() {
        return stackId;
    }
}
