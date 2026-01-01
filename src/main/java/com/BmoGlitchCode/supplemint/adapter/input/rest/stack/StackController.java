package com.BmoGlitchCode.supplemint.adapter.input.rest.stack;

import com.BmoGlitchCode.supplemint.application.dto.request.stack.AddStackItemRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.CreateStackRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.stack.UpdateStackRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.stack.StackResponse;
import com.BmoGlitchCode.supplemint.application.mapper.stack.StackDtoMapper;
import com.BmoGlitchCode.supplemint.domain.model.stack.Stack;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackItem;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.*;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.AddStackItemUseCase.AddStackItemCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.CreateStackUseCase.CreateStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.UpdateStackUseCase.UpdateStackCommand;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.GetSupplementUseCase;
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
        CreateStackCommand command = stackDtoMapper.toCreateCommand(request, request.userId());
        Stack createdStack = createStackUseCase.createStack(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(stackDtoMapper.toResponse(createdStack));
    }

    @PutMapping("/{stackId}")
    public ResponseEntity<StackResponse> updateStack(
            @PathVariable UUID stackId,
            @Valid @RequestBody UpdateStackRequest request) {
        UpdateStackCommand command = stackDtoMapper.toUpdateCommand(request, request.userId(), stackId);
        Stack updatedStack = updateStackUseCase.updateStack(command);
        return ResponseEntity.ok(stackDtoMapper.toResponse(updatedStack));
    }

    @DeleteMapping("/{stackId}")
    public ResponseEntity<Void> deleteStack(
            @PathVariable UUID stackId,
            @RequestParam UUID userId) {
        deleteStackUseCase.deleteStack(stackDtoMapper.toDeleteStackCommand(userId, stackId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{stackId}")
    public ResponseEntity<StackResponse> getStack(
            @PathVariable UUID stackId,
            @RequestParam UUID userId) {
        Stack stack = getStackUseCase.getStack(stackDtoMapper.toGetStackQuery(userId, stackId));

        Map<SupplementId, Supplement> supplementMap = stack.getItems().stream()
                .map(item -> getSupplementUseCase.get(stackDtoMapper.toGetSupplementQuery(userId, item.getSupplementId())))
                .collect(Collectors.toMap(Supplement::getId, s -> s));

        return ResponseEntity.ok(stackDtoMapper.toResponse(stack, supplementMap));
    }

    @GetMapping
    public ResponseEntity<List<StackResponse>> listUserStacks(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        List<Stack> stacks = listUserStacksUseCase.list(stackDtoMapper.toListUserStacksQuery(userId, activeOnly));

        Map<SupplementId, Supplement> supplementMap = stacks.stream()
                .flatMap(s -> s.getItems().stream())
                .map(StackItem::getSupplementId)
                .distinct()
                .map(sid -> getSupplementUseCase.get(stackDtoMapper.toGetSupplementQuery(userId, sid)))
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
        AddStackItemCommand command = stackDtoMapper.toAddStackItemCommand(request, request.userId(), stackId);
        addStackItemUseCase.addStackItem(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{stackId}/items/{supplementId}")
    public ResponseEntity<Void> removeStackItem(
            @PathVariable UUID stackId,
            @PathVariable UUID supplementId,
            @RequestParam UUID userId) {
        removeStackItemUseCase.removeStackItem(stackDtoMapper.toRemoveStackItemCommand(userId, stackId, supplementId));
        return ResponseEntity.noContent().build();
    }
}
