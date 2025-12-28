package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.CreateStackUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateStackUseCaseImpl implements CreateStackUseCase {

    private final StackRepository stackRepository;

    @Override
    public Stack createStack(CreateStackCommand command) {
        Stack stack = Stack.of(
                command.userId(),
                command.name(),
                command.description(),
                command.defaultTime(),
                command.color(),
                command.items());

        return stackRepository.save(stack);
    }
}
