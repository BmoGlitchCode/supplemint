package com.BmoGlitchCode.supplemint.domain.port.input.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.time.Instant;
import java.util.List;

/**
 * Input port for listing supplement logs with various filters.
 */
public interface ListSupplementLogsUseCase {

    /**
     * Lists supplement logs based on the provided query criteria.
     *
     * @param query the query containing filter criteria
     * @return list of matching supplement logs, ordered by takenAt descending
     */
    List<SupplementLog> list(ListSupplementLogsQuery query);

    /**
     * Query for listing supplement logs with optional filters.
     * All filter fields except userId are optional.
     *
     * @param userId       required - the user whose logs to retrieve
     * @param supplementId optional - filter by specific supplement
     * @param stackId      optional - filter by specific stack
     * @param startDate    optional - filter logs from this date/time (inclusive)
     * @param endDate      optional - filter logs until this date/time (inclusive)
     * @param skippedOnly  if true, return only skipped logs; if false, return only taken logs; if null, return all
     */
    record ListSupplementLogsQuery(
            UserId userId,
            SupplementId supplementId,
            StackId stackId,
            Instant startDate,
            Instant endDate,
            Boolean skippedOnly
    ) {
        /**
         * Convenience factory for listing all logs for a user.
         */
        public static ListSupplementLogsQuery forUser(UserId userId) {
            return new ListSupplementLogsQuery(userId, null, null, null, null, null);
        }

        /**
         * Convenience factory for listing logs for a specific supplement.
         */
        public static ListSupplementLogsQuery forSupplement(UserId userId, SupplementId supplementId) {
            return new ListSupplementLogsQuery(userId, supplementId, null, null, null, null);
        }

        /**
         * Convenience factory for listing logs within a date range.
         */
        public static ListSupplementLogsQuery forDateRange(UserId userId, Instant start, Instant end) {
            return new ListSupplementLogsQuery(userId, null, null, start, end, null);
        }
    }
}
