package com.BmoGlitchCode.supplemint.domain.port.input.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Input port for deleting a supplement log entry.
 */
public interface DeleteSupplementLogUseCase {

    /**
     * Deletes a supplement log entry.
     *
     * @param command the command containing identifiers for deletion
     */
    void delete(DeleteSupplementLogCommand command);

    /**
     * Command for deleting a supplement log.
     *
     * @param userId the user requesting deletion (for ownership verification)
     * @param logId  the ID of the log to delete
     */
    record DeleteSupplementLogCommand(
            UserId userId,
            SupplementLogId logId
    ) {}
}
