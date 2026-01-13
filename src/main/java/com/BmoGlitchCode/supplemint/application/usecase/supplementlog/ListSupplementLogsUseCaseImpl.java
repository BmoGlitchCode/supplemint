package com.BmoGlitchCode.supplemint.application.usecase.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.ListSupplementLogsUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListSupplementLogsUseCaseImpl implements ListSupplementLogsUseCase {

    private final SupplementLogRepository repository;

    @Override
    public List<SupplementLog> list(ListSupplementLogsQuery query) {
        return repository.findByFilters(
                query.userId(),
                query.supplementId(),
                query.stackId(),
                query.startDate(),
                query.endDate(),
                query.skippedOnly()
        );
    }
}
