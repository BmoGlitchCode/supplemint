package com.BmoGlitchCode.supplemint.adapter.input.rest.stack;

import com.BmoGlitchCode.supplemint.application.dto.request.stack.AddStackItemRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.CreateStackRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.UpdateStackRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.stack.StackResponse;
import com.BmoGlitchCode.supplemint.application.mapper.stack.StackDtoMapper;
import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.*;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.AddStackItemUseCase.AddStackItemCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.CreateStackUseCase.CreateStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.DeleteStackUseCase.DeleteStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.GetStackUseCase.GetStackQuery;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.ListUserStacksUseCase.ListUserStacksQuery;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.RemoveStackItemUseCase.RemoveStackItemCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.UpdateStackUseCase.UpdateStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.GetSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.GetSupplementUseCase.GetSupplementQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/stacks")
@RequiredArgsConstructor
public class StackController {

    private final CreateStackUseCase createStackUseCase;
    private final UpdateStackUseCase updateStackUseCase;
    private final DeleteStackUseCase deleteStackUseCase;
    private final GetStackUseCase getStackUseCase;
    private final ListUserStacksUseCase listUserStacksUseCase;
    private final AddStackItemUseCase addStackItemUseCase;
    private final RemoveStackItemUseCase removeStackItemUseCase;
    private final GetSupplementUseCase getSupplementUseCase;
    private final StackDtoMapper stackDtoMapper;

    @PostMapping
    public ResponseEntity<StackResponse> createStack(@Valid @RequestBody CreateStackRequest request) {
        UserId userId = UserId.of(request.userId());
        CreateStackCommand command = stackDtoMapper.toCreateCommand(request, userId.getValue());
        Stack createdStack = createStackUseCase.createStack(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(stackDtoMapper.toResponse(createdStack));
    }

    @PutMapping("/{stackId}")
    public ResponseEntity<StackResponse> updateStack(
            @PathVariable UUID stackId,
            @Valid @RequestBody UpdateStackRequest request) {
        UserId userId = UserId.of(request.userId());
        UpdateStackCommand command = stackDtoMapper.toUpdateCommand(request, userId.getValue(), stackId);
        Stack updatedStack = updateStackUseCase.updateStack(command);
        return ResponseEntity.ok(stackDtoMapper.toResponse(updatedStack));
    }

    @DeleteMapping("/{stackId}")
    public ResponseEntity<Void> deleteStack(
            @PathVariable UUID stackId,
            @RequestParam UUID userId) {
        UserId uid = UserId.of(userId);
        DeleteStackCommand command = new DeleteStackCommand(new StackId(stackId), uid);
        deleteStackUseCase.deleteStack(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{stackId}")
    public ResponseEntity<StackResponse> getStack(
            @PathVariable UUID stackId,
            @RequestParam UUID userId) {
        UserId uid = UserId.of(userId);
        GetStackQuery query = new GetStackQuery(new StackId(stackId), uid);
        Stack stack = getStackUseCase.getStack(query);

        Map<SupplementId, Supplement> supplementMap = stack.getItems().stream()
                .map(item -> getSupplementUseCase.get(new GetSupplementQuery(uid, item.getSupplementId())))
                .collect(Collectors.toMap(Supplement::getId, s -> s));

        return ResponseEntity.ok(stackDtoMapper.toResponse(stack, supplementMap));
    }

    @GetMapping
    public ResponseEntity<List<StackResponse>> listUserStacks(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        UserId uid = UserId.of(userId);
        ListUserStacksQuery query = new ListUserStacksQuery(uid, activeOnly);
        List<Stack> stacks = listUserStacksUseCase.list(query);

        // Fetch all unique supplements across all stacks
        Map<SupplementId, Supplement> supplementMap = stacks.stream()
                .flatMap(s -> s.getItems().stream())
                .map(StackItem::getSupplementId)
                .distinct()
                .map(sid -> getSupplementUseCase.get(new GetSupplementQuery(uid, sid)))
                .collect(Collectors.toMap(Supplement::getId, s -> s));

        List<StackResponse> response = stacks.stream()
                .map(stack -> stackDtoMapper.toResponse(stack, supplementMap))
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{stackId}/items")
    public ResponseEntity<Void> addStackItem(
            @PathVariable UUID stackId,
            @Valid @RequestBody AddStackItemRequest request) {
        UserId userId = UserId.of(request.userId());
        AddStackItemCommand command = stackDtoMapper.toAddStackItemCommand(request, userId.getValue(), stackId);
        addStackItemUseCase.addStackItem(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{stackId}/items/{supplementId}")
    public ResponseEntity<Void> removeStackItem(
            @PathVariable UUID stackId,
            @PathVariable UUID supplementId,
            @RequestParam UUID userId) {
        UserId uid = UserId.of(userId);
        RemoveStackItemCommand command = new RemoveStackItemCommand(new StackId(stackId), uid,
                com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId.of(supplementId));
        removeStackItemUseCase.removeStackItem(command);
        return ResponseEntity.noContent().build();
    }
}
