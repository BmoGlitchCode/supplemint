package com.BmoGlitchCode.supplemint.application.usecase.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.GetInventoryUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.SupplementInventoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GetInventoryUseCaseImpl implements GetInventoryUseCase {

    private final SupplementInventoryRepository inventoryRepository;

    @Override
    public Optional<SupplementInventory> getInventoryById(GetInventoryByIdQuery query) {
        return inventoryRepository.findById(query.inventoryId())
                .filter(inv -> inv.getUserId().equals(query.userId()));
    }

    @Override
    public List<SupplementInventory> getInventoryForSupplement(GetSupplementInventoryQuery query) {
        List<SupplementInventory> allForSupplement = inventoryRepository.findBySupplementId(query.supplementId());
        return allForSupplement.stream()
                .filter(inv -> inv.getUserId().equals(query.userId()))
                .toList();
    }
}
