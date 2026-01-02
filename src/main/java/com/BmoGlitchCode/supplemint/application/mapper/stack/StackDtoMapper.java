package com.BmoGlitchCode.supplemint.application.mapper.stack;

import com.BmoGlitchCode.supplemint.application.dto.request.stack.AddStackItemRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.CreateStackRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.StackItemRequest;
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
import com.BmoGlitchCode.supplemint.domain.port.input.stack.DeleteStackUseCase.DeleteStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.GetStackUseCase.GetStackQuery;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.ListUserStacksUseCase.ListUserStacksQuery;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.RemoveStackItemUseCase.RemoveStackItemCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.UpdateStackUseCase.UpdateStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.GetSupplementUseCase.GetSupplementQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
                toStackItems(request.items()));
    }

    public UpdateStackCommand toUpdateCommand(UpdateStackRequest request, UUID userId, UUID stackId) {
        return new UpdateStackCommand(
                StackId.of(stackId),
                UserId.of(userId),
                request.name(),
                request.description(),
                request.defaultTime(),
                request.color(),
                toStackItems(request.items()));
    }

    public AddStackItemCommand toAddStackItemCommand(AddStackItemRequest request, UUID userId, UUID stackId) {
        return new AddStackItemCommand(
                StackId.of(stackId),
                UserId.of(userId),
                StackItem.of(
                        SupplementId.of(request.supplementId()),
                        request.sortOrder() != null ? request.sortOrder() : 0));
    }

    private List<StackItem> toStackItems(List<StackItemRequest> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        List<StackItem> stackItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            StackItemRequest item = items.get(i);
            int sortOrder = item.sortOrder() != null ? item.sortOrder() : i;
            stackItems.add(StackItem.of(SupplementId.of(item.supplementId()), sortOrder));
        }
        return stackItems;
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
                item.getSortOrder());
    }

    public GetStackQuery toGetStackQuery(UUID userId, UUID stackId) {
        return new GetStackQuery(StackId.of(stackId), UserId.of(userId));
    }

    public ListUserStacksQuery toListUserStacksQuery(UUID userId, boolean activeOnly) {
        return new ListUserStacksQuery(UserId.of(userId), activeOnly);
    }

    public DeleteStackCommand toDeleteStackCommand(UUID userId, UUID stackId) {
        return new DeleteStackCommand(StackId.of(stackId), UserId.of(userId));
    }

    public RemoveStackItemCommand toRemoveStackItemCommand(UUID userId, UUID stackId, UUID supplementId) {
        return new RemoveStackItemCommand(
                StackId.of(stackId),
                UserId.of(userId),
                SupplementId.of(supplementId));
    }

    public GetSupplementQuery toGetSupplementQuery(UUID userId, SupplementId supplementId) {
        return new GetSupplementQuery(UserId.of(userId), supplementId);
    }
}
