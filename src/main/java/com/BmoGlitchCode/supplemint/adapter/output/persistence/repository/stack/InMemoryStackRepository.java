package com.BmoGlitchCode.supplemint.adapter.output.persistence.repository.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of StackRepository.
 * Suitable for development and testing.
 */
@Repository
public class InMemoryStackRepository implements StackRepository {

    private final Map<StackId, Stack> storage = new ConcurrentHashMap<>();

    @Override
    public Stack save(Stack stack) {
        storage.put(stack.getId(), stack);
        return stack;
    }

    @Override
    public Optional<Stack> findById(StackId stackId) {
        return Optional.ofNullable(storage.get(stackId));
    }

    @Override
    public List<Stack> findByUserId(UserId userId) {
        return storage.values().stream()
                .filter(stack -> stack.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(StackId stackId) {
        storage.remove(stackId);
    }

    public void clear() {
        storage.clear();
    }
}
