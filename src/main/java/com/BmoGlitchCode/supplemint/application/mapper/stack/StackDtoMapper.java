package com.BmoGlitchCode.supplemint.application.mapper.stack;

import com.BmoGlitchCode.supplemint.application.dto.request.stack.AddStackItemRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.CreateStackRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.UpdateStackRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.stack.StackItemResponse;
import com.BmoGlitchCode.supplemint.application.dto.response.stack.StackResponse;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.AddStackItemUseCase.AddStackItemCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.CreateStackUseCase.CreateStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.UpdateStackUseCase.UpdateStackCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class StackDtoMapper {

    public CreateStackCommand toCreateCommand(CreateStackRequest request, UUID userId) {
        return new CreateStackCommand(
                UserId.of(userId),
                request.name(),
                request.description(),
                request.defaultTime(),
                request.color(),
                request.items());
    }

    public UpdateStackCommand toUpdateCommand(UpdateStackRequest request, UUID userId, UUID stackId) {
        return new UpdateStackCommand(
                StackId.of(stackId),
                UserId.of(userId),
                request.name(),
                request.description(),
                request.defaultTime(),
                request.color(),
                request.items());
    }

    public AddStackItemCommand toAddStackItemCommand(AddStackItemRequest request, UUID userId, UUID stackId) {
        // Assuming validation checks if userId matches request wrapper if needed, or we
        // enforce consistency
        // Here we take userId from authentication usually, ensuring security
        return new AddStackItemCommand(
                StackId.of(stackId),
                UserId.of(userId),
                StackItem.builder()
                        .supplementId(SupplementId.of(request.supplementId()))
                        .sortOrder(request.sortOrder())
                        .notes(request.notes())
                        .build());
    }

    public StackResponse toResponse(Stack stack) {
        List<StackItemResponse> itemResponses = stack.getItems().stream()
                .map(item -> toItemResponse(item, null))
                .toList();

        return createStackResponse(stack, itemResponses);
    }

    public StackResponse toResponse(Stack stack,
            java.util.Map<SupplementId, com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement> supplementMap) {
        List<StackItemResponse> itemResponses = stack.getItems().stream()
                .map(item -> toItemResponse(item, supplementMap.get(item.getSupplementId())))
                .toList();

        return createStackResponse(stack, itemResponses);
    }

    private StackResponse createStackResponse(Stack stack, List<StackItemResponse> itemResponses) {
        return new StackResponse(
                stack.getId().value(),
                stack.getUserId().getValue(),
                stack.getName(),
                stack.getDescription(),
                stack.getDefaultTime(),
                stack.getColor(),
                stack.isActive(),
                itemResponses,
                stack.getCreatedAt(),
                stack.getUpdatedAt());
    }

    private StackItemResponse toItemResponse(StackItem item,
            com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement supplement) {
        return new StackItemResponse(
                item.getSupplementId().getValue(),
                supplement != null ? supplement.getName() : null,
                supplement != null ? supplement.getDescription() : null,
                supplement != null ? supplement.getDosagePerServing() : null,
                supplement != null ? supplement.getDosageUnit() : null,
                item.getSortOrder(),
                item.getNotes());
    }
}
