package com.BmoGlitchCode.supplemint.domain.port.output.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Output Port for Supplement persistence operations.
 * This interface is implemented by adapters (e.g., JPA repository adapter)
 * and used by the application layer to persist and retrieve supplements.
 */
public interface SupplementRepository {

    /**
     * Saves a supplement to the repository.
     * Used for both creation and updates.
     *
     * @param supplement the supplement to save
     * @return the saved supplement
     */
    Supplement save(Supplement supplement);

    /**
     * Finds a supplement by its unique identifier.
     *
     * @param id the supplement ID
     * @return an Optional containing the supplement if found, empty otherwise
     */
    Optional<Supplement> findById(SupplementId id);

    /**
     * Finds all supplements owned by a specific user.
     *
     * @param userId the user ID
     * @return a list of supplements owned by the user
     */
    List<Supplement> findByUserId(UserId userId);

    /**
     * Finds supplements owned by a specific user, filtering by active status.
     *
     * @param userId the user ID
     * @param active whether to include only active or inactive supplements
     * @return a list of supplements matching the criteria
     */
    List<Supplement> findByUserIdAndActive(UserId userId, boolean active);

    /**
     * Deletes a supplement by its unique identifier.
     *
     * @param id the supplement ID
     */
    void deleteById(SupplementId id);
}
