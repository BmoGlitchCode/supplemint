package com.BmoGlitchCode.supplemint.application.dto.request.supplementlog;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Request DTO for logging a supplement intake.
 * userId is passed as a query parameter, not in the request body.
 */
public record LogSupplementRequest(
        @NotNull(message = "Supplement ID cannot be null")
        UUID supplementId,

        UUID stackId,  // optional

        @NotNull(message = "Taken at timestamp cannot be null")
        Instant takenAt,

        @NotNull(message = "Units taken cannot be null")
        @Positive(message = "Units taken must be positive")
        BigDecimal unitsTaken,

        boolean skipped,  // default false

        @Size(max = 1000, message = "Notes must be at most 1000 characters")
        String notes
) {}
