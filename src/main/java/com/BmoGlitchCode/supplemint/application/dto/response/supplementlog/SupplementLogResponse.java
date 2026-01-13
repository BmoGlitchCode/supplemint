package com.BmoGlitchCode.supplemint.application.dto.response.supplementlog;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO for supplement log entries.
 */
@Builder
public record SupplementLogResponse(
        UUID id,
        UUID userId,
        UUID supplementId,
        UUID stackId, // nullable
        Instant takenAt,
        BigDecimal unitsTaken,
        boolean skipped,
        String notes,
        Instant createdAt
) {
}
