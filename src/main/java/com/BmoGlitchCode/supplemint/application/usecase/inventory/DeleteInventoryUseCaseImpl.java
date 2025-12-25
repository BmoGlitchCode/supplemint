package com.BmoGlitchCode.supplemint.application.usecase.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.DeleteInventoryUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.SupplementInventoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteInventoryUseCaseImpl implements DeleteInventoryUseCase {

    private final SupplementInventoryRepository inventoryRepository;

    @Override
    public void deleteInventory(DeleteInventoryQuery query) {
        SupplementInventory inventory = inventoryRepository.findById(query.inventoryId())
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found with id: " + query.inventoryId()));

        if (!inventory.getUserId().equals(query.userId())) {
            throw new IllegalArgumentException("Inventory does not belong to user");
        }

        inventory.markAsEmpty();
        inventoryRepository.save(inventory);
    }
}
