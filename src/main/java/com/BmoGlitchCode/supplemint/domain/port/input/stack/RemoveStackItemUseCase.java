package com.BmoGlitchCode.supplemint.domain.port.input.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Input port for removing an item from a stack.
 */
public interface RemoveStackItemUseCase {

    void removeStackItem(RemoveStackItemCommand command);

    record RemoveStackItemCommand(
            StackId stackId,
            UserId userId, // For validation
            SupplementId supplementId) {
    }
}
