package com.BmoGlitchCode.supplemint.domain.port.input.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Input port for adding an item to a stack.
 */
public interface AddStackItemUseCase {

    void addStackItem(AddStackItemCommand command);

    record AddStackItemCommand(
            StackId stackId,
            UserId userId, // For validation
            StackItem stackItem) {
    }
}
