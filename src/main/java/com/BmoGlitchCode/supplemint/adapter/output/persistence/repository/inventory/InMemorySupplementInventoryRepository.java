package com.BmoGlitchCode.supplemint.adapter.output.persistence.repository.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventoryId;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.output.SupplementInventoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of SupplementInventoryRepository.
 * Stores supplement inventory records in a ConcurrentHashMap for thread-safe
 * operations.
 * Suitable for development and testing.
 */
@Repository
public class InMemorySupplementInventoryRepository implements SupplementInventoryRepository {

    private final Map<SupplementInventoryId, SupplementInventory> storage = new ConcurrentHashMap<>();

    @Override
    public SupplementInventory save(SupplementInventory inventory) {
        storage.put(inventory.getId(), inventory);
        return inventory;
    }

    @Override
    public Optional<SupplementInventory> findById(SupplementInventoryId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<SupplementInventory> findByUserId(UserId userId) {
        return storage.values().stream()
                .filter(inventory -> inventory.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplementInventory> findBySupplementId(SupplementId supplementId) {
        return storage.values().stream()
                .filter(inventory -> inventory.getSupplementId().equals(supplementId))
                .collect(Collectors.toList());
    }

    /**
     * Clears all inventory records from the repository.
     * Useful for testing.
     */
    public void clear() {
        storage.clear();
    }
}
