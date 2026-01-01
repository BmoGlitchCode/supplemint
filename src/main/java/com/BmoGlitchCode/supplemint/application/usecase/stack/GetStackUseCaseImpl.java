package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.GetStackUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetStackUseCaseImpl implements GetStackUseCase {

    private final StackRepository stackRepository;

    @Override
    public Stack getStack(GetStackQuery query) {
        StackId id = query.stackId();
        Stack stack = stackRepository.findById(id)
                .orElseThrow(() -> new StackNotFoundException(id));

        if (!stack.getUserId().equals(query.userId())) {
            throw new StackAccessDeniedException(id, query.userId());
        }

        return stack;
    }
}
