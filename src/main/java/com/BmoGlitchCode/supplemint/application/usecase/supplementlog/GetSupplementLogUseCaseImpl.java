package com.BmoGlitchCode.supplemint.application.usecase.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.GetSupplementLogUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSupplementLogUseCaseImpl implements GetSupplementLogUseCase {

    private final SupplementLogRepository repository;

    @Override
    public SupplementLog get(GetSupplementLogQuery query) {
        SupplementLog log = repository.findById(query.logId())
                .orElseThrow(() -> new SupplementLogNotFoundException(query.logId()));

        if (!log.belongsToUser(query.userId())) {
            throw new SupplementLogAccessDeniedException(query.logId(), query.userId());
        }

        return log;
    }
}
