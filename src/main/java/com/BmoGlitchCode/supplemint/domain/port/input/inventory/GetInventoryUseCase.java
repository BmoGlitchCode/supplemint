package com.BmoGlitchCode.supplemint.domain.port.input.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventoryId;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

public interface GetInventoryUseCase {

    Optional<SupplementInventory> getInventoryById(GetInventoryByIdQuery query);

    List<SupplementInventory> getInventoryForSupplement(GetSupplementInventoryQuery query);

    record GetInventoryByIdQuery(UserId userId, SupplementInventoryId inventoryId) {
    }

    record GetSupplementInventoryQuery(UserId userId, SupplementId supplementId) {
    }
}
