package com.BmoGlitchCode.supplemint.domain.port.input.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.stack.TimeOfDay;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;

/**
 * Input port for creating a new stack.
 */
public interface CreateStackUseCase {

    Stack createStack(CreateStackCommand command);

    record CreateStackCommand(
            UserId userId,
            String name,
            String description,
            TimeOfDay defaultTime,
            String color,
            List<StackItem> items) {
    }
}
