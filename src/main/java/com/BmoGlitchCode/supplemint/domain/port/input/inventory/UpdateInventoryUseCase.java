package com.BmoGlitchCode.supplemint.domain.port.input.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventoryId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.math.BigDecimal;

public interface UpdateInventoryUseCase {

    SupplementInventory updateInventory(UpdateInventoryCommand command);

    record UpdateInventoryCommand(
            UserId userId,
            SupplementInventoryId inventoryId,
            BigDecimal remainingUnits,
            String notes) {
    }
}
