package com.BmoGlitchCode.supplemint.application.usecase.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.AddInventoryUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.SupplementInventoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddInventoryUseCaseImpl implements AddInventoryUseCase {

    private final SupplementInventoryRepository inventoryRepository;

    @Override
    public SupplementInventory addInventory(AddInventoryCommand command) {
        SupplementInventory inventory = SupplementInventory.createNew(
                command.userId(),
                command.supplementId(),
                command.purchaseDate(),
                command.totalUnits(),
                command.unitType(),
                command.labelServings(),
                command.unitsPerServing(),
                command.dosagePerServing(),
                command.dosageUnit(),
                command.expirationDate(),
                command.cost(),
                command.vendor(),
                command.batchNumber(),
                command.notes());

        return inventoryRepository.save(inventory);
    }
}
