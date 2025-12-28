package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.AddStackItemUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddStackItemUseCaseImpl implements AddStackItemUseCase {

    private final StackRepository stackRepository;

    @Override
    public void addStackItem(AddStackItemCommand command) {
        Stack stack = stackRepository.findById(command.stackId())
                .orElseThrow(() -> new IllegalArgumentException("Stack not found with id: " + command.stackId()));

        if (!stack.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("Stack does not belong to user");
        }

        stack.addItem(command.stackItem());
        stackRepository.save(stack);
    }
}
