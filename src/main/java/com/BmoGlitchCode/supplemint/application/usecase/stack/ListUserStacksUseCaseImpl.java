package com.BmoGlitchCode.supplemint.application.usecase.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.ListUserStacksUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListUserStacksUseCaseImpl implements ListUserStacksUseCase {

    private final StackRepository stackRepository;

    @Override
    public List<Stack> list(ListUserStacksQuery query) {
        List<Stack> stacks = stackRepository.findByUserId(query.userId());

        if (query.activeOnly()) {
            return stacks.stream()
                    .filter(Stack::isActive)
                    .toList();
        }

        return stacks;
    }
}
