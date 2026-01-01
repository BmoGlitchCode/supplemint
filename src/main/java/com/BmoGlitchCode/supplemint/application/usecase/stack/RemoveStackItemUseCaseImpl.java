package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.RemoveStackItemUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveStackItemUseCaseImpl implements RemoveStackItemUseCase {

    private final StackRepository stackRepository;

    @Override
    public void removeStackItem(RemoveStackItemCommand command) {
        StackId id = command.stackId();
        Stack stack = stackRepository.findById(id)
                .orElseThrow(() -> new StackNotFoundException(id));

        if (!stack.getUserId().equals(command.userId())) {
            throw new StackAccessDeniedException(id, command.userId());
        }

        stack.removeItem(command.supplementId());
        stackRepository.save(stack);
    }
}
