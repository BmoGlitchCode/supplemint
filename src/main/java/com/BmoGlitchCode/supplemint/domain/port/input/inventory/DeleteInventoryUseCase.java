package com.BmoGlitchCode.supplemint.domain.port.input.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventoryId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

public interface DeleteInventoryUseCase {

    void deleteInventory(DeleteInventoryQuery query);

    record DeleteInventoryQuery(UserId userId, SupplementInventoryId inventoryId) {
    }
}
