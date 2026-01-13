package com.BmoGlitchCode.supplemint.domain.port.input.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

/**
 * Input port for retrieving a specific supplement log entry.
 */
public interface GetSupplementLogUseCase {

    /**
     * Retrieves a supplement log by ID.
     *
     * @param query the query containing user and log identifiers
     * @return the supplement log
     */
    SupplementLog get(GetSupplementLogQuery query);

    /**
     * Query for retrieving a supplement log.
     *
     * @param userId the user requesting the log (for ownership verification)
     * @param logId  the ID of the log to retrieve
     */
    record GetSupplementLogQuery(
            UserId userId,
            SupplementLogId logId
    ) {}
}
