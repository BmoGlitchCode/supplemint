package com.BmoGlitchCode.supplemint.application.dto.request.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.stack.TimeOfDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateStackRequest(
        @NotNull(message = "User ID cannot be null") UUID userId,

        @NotBlank(message = "Name cannot be blank") String name,

        String description,

        TimeOfDay defaultTime,

        String color,

        List<StackItem> items) {
}
