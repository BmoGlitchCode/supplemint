package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;

import com.BmoGlitchCode.supplemint.domain.port.input.stack.GetStackUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetStackUseCaseImpl implements GetStackUseCase {

    private final StackRepository stackRepository;

    @Override
    public Stack getStack(GetStackQuery query) {
        Stack stack = stackRepository.findById(query.stackId())
                .orElseThrow(() -> new IllegalArgumentException("Stack not found with id: " + query.stackId()));

        if (!stack.getUserId().equals(query.userId())) {
            throw new IllegalArgumentException("Stack does not belong to user");
        }

        return stack;
    }

}
