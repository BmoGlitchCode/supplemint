package com.BmoGlitchCode.supplemint.domain.port.output.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Output Port for SupplementLog persistence operations.
 * This interface is implemented by adapters (e.g., JPA repository adapter, in-memory)
 * and used by the application layer to persist and retrieve supplement logs.
 */
public interface SupplementLogRepository {

    /**
     * Saves a supplement log to the repository.
     *
     * @param log the supplement log to save
     * @return the saved supplement log
     */
    SupplementLog save(SupplementLog log);

    /**
     * Finds a supplement log by its unique identifier.
     *
     * @param id the log ID
     * @return an Optional containing the log if found, empty otherwise
     */
    Optional<SupplementLog> findById(SupplementLogId id);

    /**
     * Finds all supplement logs for a specific user.
     *
     * @param userId the user ID
     * @return list of logs owned by the user, ordered by takenAt descending
     */
    List<SupplementLog> findByUserId(UserId userId);

    /**
     * Finds all supplement logs for a specific supplement.
     *
     * @param userId       the user ID (for ownership)
     * @param supplementId the supplement ID
     * @return list of logs for the supplement, ordered by takenAt descending
     */
    List<SupplementLog> findByUserIdAndSupplementId(UserId userId, SupplementId supplementId);

    /**
     * Finds all supplement logs associated with a specific stack.
     *
     * @param userId  the user ID (for ownership)
     * @param stackId the stack ID
     * @return list of logs associated with the stack, ordered by takenAt descending
     */
    List<SupplementLog> findByUserIdAndStackId(UserId userId, StackId stackId);

    /**
     * Finds supplement logs within a date range.
     *
     * @param userId    the user ID
     * @param startDate the start of the range (inclusive)
     * @param endDate   the end of the range (inclusive)
     * @return list of logs within the range, ordered by takenAt descending
     */
    List<SupplementLog> findByUserIdAndDateRange(UserId userId, Instant startDate, Instant endDate);

    /**
     * Finds supplement logs with combined filters.
     * This is a flexible query method that supports optional filtering.
     *
     * @param userId       the user ID (required)
     * @param supplementId optional - filter by supplement
     * @param stackId      optional - filter by stack
     * @param startDate    optional - filter from date
     * @param endDate      optional - filter to date
     * @param skipped      optional - filter by skipped status
     * @return list of matching logs, ordered by takenAt descending
     */
    List<SupplementLog> findByFilters(
            UserId userId,
            SupplementId supplementId,
            StackId stackId,
            Instant startDate,
            Instant endDate,
            Boolean skipped
    );

    /**
     * Deletes a supplement log by its unique identifier.
     *
     * @param id the log ID
     */
    void deleteById(SupplementLogId id);

    /**
     * Counts the number of logs for a specific supplement.
     * Useful for checking if a supplement can be deleted.
     *
     * @param supplementId the supplement ID
     * @return the count of logs referencing this supplement
     */
    long countBySupplementId(SupplementId supplementId);

    /**
     * Clears stack association for all logs referencing a specific stack.
     * Called when a stack is deleted (SET NULL behavior from schema).
     *
     * @param stackId the stack ID being deleted
     */
    void clearStackReferences(StackId stackId);
}
