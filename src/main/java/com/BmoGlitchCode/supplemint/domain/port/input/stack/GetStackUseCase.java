package com.BmoGlitchCode.supplemint.domain.port.input.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Input port for retrieving stacks.
 */
public interface GetStackUseCase {

    Stack getStack(GetStackQuery query);

    record GetStackQuery(
            StackId stackId,
            UserId userId // For validation
    ) {
    }
}
