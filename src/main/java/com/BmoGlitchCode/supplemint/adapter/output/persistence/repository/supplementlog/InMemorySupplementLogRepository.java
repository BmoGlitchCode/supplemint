package com.BmoGlitchCode.supplemint.adapter.output.persistence.repository.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of SupplementLogRepository.
 * Stores supplement logs in a ConcurrentHashMap for thread-safe operations.
 * Suitable for development and testing.
 */
@Repository
public class InMemorySupplementLogRepository implements SupplementLogRepository {

    private final Map<SupplementLogId, SupplementLog> storage = new ConcurrentHashMap<>();

    @Override
    public SupplementLog save(SupplementLog log) {
        storage.put(log.getId(), log);
        return log;
    }

    @Override
    public Optional<SupplementLog> findById(SupplementLogId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<SupplementLog> findByUserId(UserId userId) {
        return storage.values().stream()
                .filter(log -> log.belongsToUser(userId))
                .sorted(byTakenAtDescending())
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplementLog> findByUserIdAndSupplementId(UserId userId, SupplementId supplementId) {
        return storage.values().stream()
                .filter(log -> log.belongsToUser(userId) && log.isForSupplement(supplementId))
                .sorted(byTakenAtDescending())
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplementLog> findByUserIdAndStackId(UserId userId, StackId stackId) {
        return storage.values().stream()
                .filter(log -> log.belongsToUser(userId) && stackId.equals(log.getStackId()))
                .sorted(byTakenAtDescending())
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplementLog> findByUserIdAndDateRange(UserId userId, Instant startDate, Instant endDate) {
        return storage.values().stream()
                .filter(log -> log.belongsToUser(userId))
                .filter(log -> isWithinDateRange(log.getTakenAt(), startDate, endDate))
                .sorted(byTakenAtDescending())
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplementLog> findByFilters(
            UserId userId,
            SupplementId supplementId,
            StackId stackId,
            Instant startDate,
            Instant endDate,
            Boolean skipped) {

        return storage.values().stream()
                .filter(log -> log.belongsToUser(userId))
                .filter(log -> supplementId == null || log.isForSupplement(supplementId))
                .filter(log -> stackId == null || stackId.equals(log.getStackId()))
                .filter(log -> startDate == null || !log.getTakenAt().isBefore(startDate))
                .filter(log -> endDate == null || !log.getTakenAt().isAfter(endDate))
                .filter(log -> skipped == null || log.isSkipped() == skipped)
                .sorted(byTakenAtDescending())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(SupplementLogId id) {
        storage.remove(id);
    }

    @Override
    public long countBySupplementId(SupplementId supplementId) {
        return storage.values().stream()
                .filter(log -> log.isForSupplement(supplementId))
                .count();
    }

    @Override
    public void clearStackReferences(StackId stackId) {
        // In-memory implementation: we need to update logs to remove stack reference
        // Since SupplementLog.stackId is final, we need to rebuild the log
        // For simplicity in this in-memory impl, we'll just note this limitation
        // A real JPA implementation would use an UPDATE query
        storage.values().stream()
                .filter(log -> stackId.equals(log.getStackId()))
                .forEach(log -> {
                    // Create a new log without the stack reference
                    SupplementLog updatedLog = log.toBuilder()
                            .stackId(null)
                            .build();
                    storage.put(log.getId(), updatedLog);
                });
    }

    /**
     * Clears all logs from the repository.
     * Useful for testing.
     */
    public void clear() {
        storage.clear();
    }

    private Comparator<SupplementLog> byTakenAtDescending() {
        return Comparator.comparing(SupplementLog::getTakenAt).reversed();
    }

    private boolean isWithinDateRange(Instant timestamp, Instant start, Instant end) {
        return !timestamp.isBefore(start) && !timestamp.isAfter(end);
    }
}
