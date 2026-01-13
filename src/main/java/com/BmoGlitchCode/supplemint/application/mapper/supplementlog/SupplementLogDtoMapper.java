package com.BmoGlitchCode.supplemint.application.mapper.supplementlog;

import com.BmoGlitchCode.supplemint.application.dto.request.supplementlog.LogSupplementRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.supplementlog.UpdateSupplementLogRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.supplementlog.SupplementLogResponse;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLogId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.DeleteSupplementLogUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.GetSupplementLogUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.ListSupplementLogsUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.LogSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.UpdateSupplementLogUseCase;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class SupplementLogDtoMapper {

    // Request → Command
    public LogSupplementUseCase.LogSupplementCommand toLogCommand(UUID userId, LogSupplementRequest request) {
        if (request == null) {
            return null;
        }
        return new LogSupplementUseCase.LogSupplementCommand(
                UserId.of(userId),
                SupplementId.of(request.supplementId()),
                request.stackId() != null ? StackId.of(request.stackId()) : null,
                request.takenAt(),
                request.unitsTaken(),
                request.skipped(),
                request.notes()
        );
    }

    public UpdateSupplementLogUseCase.UpdateSupplementLogCommand toUpdateCommand(
            UUID userId, UUID logId, UpdateSupplementLogRequest request) {
        if (request == null) {
            return null;
        }
        return new UpdateSupplementLogUseCase.UpdateSupplementLogCommand(
                UserId.of(userId),
                SupplementLogId.of(logId),
                request.skipped(),
                request.notes()
        );
    }

    // Parameters → Query/Command
    public GetSupplementLogUseCase.GetSupplementLogQuery toGetQuery(UUID userId, UUID logId) {
        return new GetSupplementLogUseCase.GetSupplementLogQuery(
                UserId.of(userId),
                SupplementLogId.of(logId)
        );
    }

    public ListSupplementLogsUseCase.ListSupplementLogsQuery toListQuery(
            UUID userId,
            UUID supplementId,
            UUID stackId,
            Instant startDate,
            Instant endDate,
            Boolean skippedOnly) {
        return new ListSupplementLogsUseCase.ListSupplementLogsQuery(
                UserId.of(userId),
                supplementId != null ? SupplementId.of(supplementId) : null,
                stackId != null ? StackId.of(stackId) : null,
                startDate,
                endDate,
                skippedOnly
        );
    }

    public DeleteSupplementLogUseCase.DeleteSupplementLogCommand toDeleteCommand(UUID userId, UUID logId) {
        return new DeleteSupplementLogUseCase.DeleteSupplementLogCommand(
                UserId.of(userId),
                SupplementLogId.of(logId)
        );
    }

    // Domain → Response
    public SupplementLogResponse toResponse(SupplementLog log) {
        if (log == null) {
            return null;
        }
        return SupplementLogResponse.builder()
                .id(log.getId().getValue())
                .userId(log.getUserId().getValue())
                .supplementId(log.getSupplementId().getValue())
                .stackId(log.getStackId() != null ? log.getStackId().value() : null)
                .takenAt(log.getTakenAt())
                .unitsTaken(log.getUnitsTaken())
                .skipped(log.isSkipped())
                .notes(log.getNotes())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
