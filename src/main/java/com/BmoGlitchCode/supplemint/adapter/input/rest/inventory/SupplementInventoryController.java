package com.BmoGlitchCode.supplemint.adapter.input.rest.inventory;

import com.BmoGlitchCode.supplemint.application.dto.request.inventory.AddInventoryRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.inventory.UpdateInventoryRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.inventory.SupplementInventoryResponse;
import com.BmoGlitchCode.supplemint.application.mapper.inventory.SupplementInventoryDtoMapper;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventoryId;

import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class SupplementInventoryController {

    private final AddInventoryUseCase addInventoryUseCase;
    private final UpdateInventoryUseCase updateInventoryUseCase;
    private final GetInventoryUseCase getInventoryUseCase;
    private final ListUserInventoryUseCase listUserInventoryUseCase;
    private final DeleteInventoryUseCase deleteInventoryUseCase;
    private final SupplementInventoryDtoMapper mapper;

    @PostMapping
    public ResponseEntity<SupplementInventoryResponse> addInventory(@RequestBody @Valid AddInventoryRequest request) {
        AddInventoryUseCase.AddInventoryCommand command = mapper.toAddCommand(request);
        SupplementInventory inventory = addInventoryUseCase.addInventory(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(inventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplementInventoryResponse> updateInventory(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateInventoryRequest request) {
        UpdateInventoryUseCase.UpdateInventoryCommand command = mapper.toUpdateCommand(id, request);
        SupplementInventory inventory = updateInventoryUseCase.updateInventory(command);
        return ResponseEntity.ok(mapper.toResponse(inventory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplementInventoryResponse> getInventoryById(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        // We need a Query object for GetInventoryById
        GetInventoryUseCase.GetInventoryByIdQuery query = new GetInventoryUseCase.GetInventoryByIdQuery(
                UserId.of(userId),
                new SupplementInventoryId(id));

        return getInventoryUseCase.getInventoryById(query)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SupplementInventoryResponse>> listUserInventory(@RequestParam UUID userId) {
        // We need a Query object for ListUserInventory
        ListUserInventoryUseCase.ListUserInventoryQuery query = mapper.toListQuery(userId);

        List<SupplementInventory> inventoryList = listUserInventoryUseCase.getUserInventory(query);

        List<SupplementInventoryResponse> responseList = inventoryList.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(
            @PathVariable UUID id,
            @RequestParam UUID userId) {

        DeleteInventoryUseCase.DeleteInventoryQuery query = new DeleteInventoryUseCase.DeleteInventoryQuery(
                UserId.of(userId),
                new SupplementInventoryId(id));

        deleteInventoryUseCase.deleteInventory(query);
        return ResponseEntity.noContent().build();
    }
}
