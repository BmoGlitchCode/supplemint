package com.BmoGlitchCode.supplemint.domain.port.input.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Input port for deleting a stack.
 */
public interface DeleteStackUseCase {

    void deleteStack(DeleteStackCommand command);

    record DeleteStackCommand(
            StackId stackId,
            UserId userId // For validation
    ) {
    }
}
