package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.UpdateStackUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateStackUseCaseImpl implements UpdateStackUseCase {

    private final StackRepository stackRepository;

    @Override
    public Stack updateStack(UpdateStackCommand command) {
        Stack stack = stackRepository.findById(command.stackId())
                .orElseThrow(() -> new IllegalArgumentException("Stack not found with id: " + command.stackId()));

        if (!stack.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("Stack does not belong to user");
        }

        stack.updateInfo(
                command.name(),
                command.description(),
                command.defaultTime(),
                command.color());

        stack.setItems(command.items());

        return stackRepository.save(stack);
    }
}
