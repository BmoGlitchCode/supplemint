package com.BmoGlitchCode.supplemint.application.dto.request.supplementlog;

import jakarta.validation.constraints.Size;

/**
 * Request DTO for updating a supplement log.
 * Both fields are optional - null means "don't change".
 */
public record UpdateSupplementLogRequest(
        Boolean skipped,  // optional - null means don't change

        @Size(max = 1000, message = "Notes must be at most 1000 characters")
        String notes  // optional - null means don't change, empty string to clear
) {}
