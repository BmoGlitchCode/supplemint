package com.BmoGlitchCode.supplemint.application.usecase.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.DeleteSupplementLogUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSupplementLogUseCaseImpl implements DeleteSupplementLogUseCase {

    private final SupplementLogRepository supplementLogRepository;
    private final SupplementRepository supplementRepository;

    @Override
    public void delete(DeleteSupplementLogCommand command) {
        SupplementLog log = supplementLogRepository.findById(command.logId())
                .orElseThrow(() -> new SupplementLogNotFoundException(command.logId()));

        if (!log.belongsToUser(command.userId())) {
            throw new SupplementLogAccessDeniedException(command.logId(), command.userId());
        }

        // If the log was for a taken supplement (not skipped), restore the units
        if (!log.isSkipped()) {
            supplementRepository.findById(log.getSupplementId())
                    .ifPresent(supplement -> {
                        supplement.incrementUnits(log.getUnitsTaken());
                        supplementRepository.save(supplement);
                    });
        }

        supplementLogRepository.deleteById(command.logId());
    }
}
