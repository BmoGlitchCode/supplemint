package com.BmoGlitchCode.supplemint.application.dto.request.stack;

import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.stack.TimeOfDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UpdateStackRequest(
        @NotNull(message = "User ID cannot be null") UUID userId,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be at most 100 characters") String name,

        @Size(max = 500, message = "Description must be at most 500 characters") String description,

        TimeOfDay defaultTime,

        @Size(max = 7, message = "Color must be at most 7 characters")
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})?$", message = "Color must be a valid hex color code") String color,

        List<StackItem> items) {
}
