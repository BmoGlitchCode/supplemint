package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.DeleteStackUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteStackUseCaseImpl implements DeleteStackUseCase {

    private final StackRepository stackRepository;

    @Override
    public void deleteStack(DeleteStackCommand command) {
        StackId id = command.stackId();
        Stack stack = stackRepository.findById(id)
                .orElseThrow(() -> new StackNotFoundException(id));

        if (!stack.getUserId().equals(command.userId())) {
            throw new StackAccessDeniedException(id, command.userId());
        }

        stackRepository.delete(id);
    }
}
