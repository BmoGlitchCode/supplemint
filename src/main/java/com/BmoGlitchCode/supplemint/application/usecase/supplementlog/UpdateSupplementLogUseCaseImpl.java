package com.BmoGlitchCode.supplemint.application.usecase.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.UpdateSupplementLogUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSupplementLogUseCaseImpl implements UpdateSupplementLogUseCase {

    private final SupplementLogRepository repository;

    @Override
    public SupplementLog update(UpdateSupplementLogCommand command) {
        SupplementLog log = repository.findById(command.logId())
                .orElseThrow(() -> new SupplementLogNotFoundException(command.logId()));

        if (!log.belongsToUser(command.userId())) {
            throw new SupplementLogAccessDeniedException(command.logId(), command.userId());
        }

        if (command.skipped() != null) {
            if (command.skipped()) {
                log.markAsSkipped();
            } else {
                log.markAsTaken();
            }
        }

        if (command.notes() != null) {
            log.updateNotes(command.notes());
        }

        return repository.save(log);
    }
}
