package com.BmoGlitchCode.supplemint.application.usecase.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.UpdateInventoryUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.SupplementInventoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateInventoryUseCaseImpl implements UpdateInventoryUseCase {

    private final SupplementInventoryRepository inventoryRepository;

    @Override
    public SupplementInventory updateInventory(UpdateInventoryCommand command) {
        SupplementInventory inventory = inventoryRepository.findById(command.inventoryId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Inventory not found with id: " + command.inventoryId()));

        if (!inventory.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("Inventory does not belong to user");
        }

        if (command.remainingUnits() != null) {
            inventory.updateRemainingUnits(command.remainingUnits());
        }

        // Logic for future updates (e.g. notes) would go here

        return inventoryRepository.save(inventory);
    }
}
