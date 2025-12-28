package com.BmoGlitchCode.supplemint.domain.port.output.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Output port for Stack persistence.
 */
public interface StackRepository {

    Stack save(Stack stack);

    Optional<Stack> findById(StackId stackId);

    List<Stack> findByUserId(UserId userId);

    void delete(StackId stackId);
}
