package com.BmoGlitchCode.supplemint.domain.port.input.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;

/**
 * Input port for listing a user's stacks.
 */
public interface ListUserStacksUseCase {

    List<Stack> list(ListUserStacksQuery query);

    record ListUserStacksQuery(
            UserId userId,
            boolean activeOnly // If true, return only active stacks (non-deleted/archived)
    ) {
    }
}
