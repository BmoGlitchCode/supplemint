package com.BmoGlitchCode.supplemint.application.usecase.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.ListUserInventoryUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.SupplementInventoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListUserInventoryUseCaseImpl implements ListUserInventoryUseCase {

    private final SupplementInventoryRepository inventoryRepository;

    @Override
    public List<SupplementInventory> getUserInventory(ListUserInventoryQuery query) {
        return inventoryRepository.findByUserId(query.userId());
    }
}
