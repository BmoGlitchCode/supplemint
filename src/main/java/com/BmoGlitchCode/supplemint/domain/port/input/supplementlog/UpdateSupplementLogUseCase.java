package com.BmoGlitchCode.supplemint.domain.port.input.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Input port for updating a supplement log entry.
 * Only allows updating mutable fields: skipped status and notes.
 */
public interface UpdateSupplementLogUseCase {

    /**
     * Updates a supplement log entry.
     *
     * @param command the command containing update details
     * @return the updated supplement log
     */
    SupplementLog update(UpdateSupplementLogCommand command);

    /**
     * Command for updating a supplement log.
     * Both skipped and notes are optional; null values mean "don't change".
     *
     * @param userId  the user requesting the update (for ownership verification)
     * @param logId   the ID of the log to update
     * @param skipped optional - new skipped status (null to keep current)
     * @param notes   optional - new notes (null to keep current, empty string to clear)
     */
    record UpdateSupplementLogCommand(
            UserId userId,
            SupplementLogId logId,
            Boolean skipped,
            String notes
    ) {}
}
