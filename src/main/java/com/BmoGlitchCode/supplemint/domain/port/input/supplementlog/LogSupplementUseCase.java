package com.BmoGlitchCode.supplemint.domain.port.input.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Input port for logging a supplement intake.
 */
public interface LogSupplementUseCase {

    /**
     * Creates a new supplement log entry.
     *
     * @param command the command containing log details
     * @return the created supplement log
     */
    SupplementLog log(LogSupplementCommand command);

    /**
     * Command for logging a supplement intake or skipped dose.
     *
     * @param userId       the user creating the log
     * @param supplementId the supplement being logged
     * @param stackId      optional - the stack this log is associated with (null if standalone)
     * @param takenAt      when the supplement was taken (or was scheduled to be taken if skipped)
     * @param unitsTaken   number of units taken (e.g., pills, scoops)
     * @param skipped      whether this dose was skipped
     * @param notes        optional notes about the intake
     */
    record LogSupplementCommand(
            UserId userId,
            SupplementId supplementId,
            StackId stackId,
            Instant takenAt,
            BigDecimal unitsTaken,
            boolean skipped,
            String notes
    ) {}
}
