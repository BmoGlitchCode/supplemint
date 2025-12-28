package com.BmoGlitchCode.supplemint.domain.port.input.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.stack.TimeOfDay;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;

/**
 * Input port for updating an existing stack.
 */
public interface UpdateStackUseCase {

    Stack updateStack(UpdateStackCommand command);

    record UpdateStackCommand(
            StackId stackId,
            UserId userId, // For validation/ownership check
            String name,
            String description,
            TimeOfDay defaultTime,
            String color,
            List<StackItem> items // Full replacement of items
    ) {
    }
}
