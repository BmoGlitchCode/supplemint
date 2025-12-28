package com.BmoGlitchCode.supplemint.application.dto.response.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.TimeOfDay;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record StackResponse(
        UUID id,
        UUID userId,
        String name,
        String description,
        TimeOfDay defaultTime,
        String color,
        boolean active,
        List<StackItemResponse> items,
        Instant createdAt,
        Instant updatedAt) {
}
