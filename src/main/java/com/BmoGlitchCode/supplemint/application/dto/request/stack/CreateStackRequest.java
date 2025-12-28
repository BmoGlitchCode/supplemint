package com.BmoGlitchCode.supplemint.application.dto.request.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.stack.TimeOfDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateStackRequest(
        @NotNull(message = "User ID cannot be null") UUID userId,

        @NotBlank(message = "Name cannot be blank") String name,

        String description,

        TimeOfDay defaultTime,

        String color,

        List<StackItem> items // Simplified: Using domain entity directly for list items for now, or should
                              // use dedicated ItemRequest? Usually dedicated.
) {
}
