package com.BmoGlitchCode.supplemint.application.usecase.supplementlog;

import com.BmoGlitchCode.supplemint.application.usecase.supplement.SupplementNotFoundException;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.LogSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogSupplementUseCaseImpl implements LogSupplementUseCase {

    private final SupplementLogRepository supplementLogRepository;
    private final SupplementRepository supplementRepository;

    @Override
    public SupplementLog log(LogSupplementCommand command) {
        // Fetch the supplement to validate it exists
        Supplement supplement = supplementRepository.findById(command.supplementId())
                .orElseThrow(() -> new SupplementNotFoundException(command.supplementId()));

        SupplementLog log;
        if (command.skipped()) {
            log = SupplementLog.ofSkipped(
                    command.userId(),
                    command.supplementId(),
                    command.stackId(),
                    command.takenAt(),
                    command.unitsTaken(),
                    command.notes());
        } else {
            // Decrement the remaining units when supplement is actually taken
            supplement.decrementUnits(command.unitsTaken());
            supplementRepository.save(supplement);

            log = SupplementLog.of(
                    command.userId(),
                    command.supplementId(),
                    command.stackId(),
                    command.takenAt(),
                    command.unitsTaken(),
                    command.notes());
        }

        return supplementLogRepository.save(log);
    }
}
