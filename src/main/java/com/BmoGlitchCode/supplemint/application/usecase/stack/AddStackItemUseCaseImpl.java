package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.AddStackItemUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddStackItemUseCaseImpl implements AddStackItemUseCase {

    private final StackRepository stackRepository;

    @Override
    public void addStackItem(AddStackItemCommand command) {
        StackId id = command.stackId();
        Stack stack = stackRepository.findById(id)
                .orElseThrow(() -> new StackNotFoundException(id));

        if (!stack.getUserId().equals(command.userId())) {
            throw new StackAccessDeniedException(id, command.userId());
        }

        stack.addItem(command.stackItem());
        stackRepository.save(stack);
    }
}
