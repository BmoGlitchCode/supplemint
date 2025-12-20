package com.BmoGlitchCode.supplemint.adapter.output.persistence.repository.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of SupplementRepository.
 * Stores supplements in a ConcurrentHashMap for thread-safe operations.
 * Suitable for development and testing.
 */
@Repository
public class InMemorySupplementRepository implements SupplementRepository {

    private final Map<SupplementId, Supplement> storage = new ConcurrentHashMap<>();

    @Override
    public Supplement save(Supplement supplement) {
        storage.put(supplement.getId(), supplement);
        return supplement;
    }

    @Override
    public Optional<Supplement> findById(SupplementId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Supplement> findByUserId(UserId userId) {
        return storage.values().stream()
                .filter(supplement -> supplement.belongsToUser(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Supplement> findByUserIdAndActive(UserId userId, boolean active) {
        return storage.values().stream()
                .filter(supplement -> supplement.belongsToUser(userId) && supplement.isActive() == active)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(SupplementId id) {
        storage.remove(id);
    }

    /**
     * Clears all supplements from the repository.
     * Useful for testing.
     */
    public void clear() {
        storage.clear();
    }
}
