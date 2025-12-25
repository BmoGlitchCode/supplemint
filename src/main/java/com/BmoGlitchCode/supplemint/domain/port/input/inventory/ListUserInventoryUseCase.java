package com.BmoGlitchCode.supplemint.domain.port.input.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;

public interface ListUserInventoryUseCase {

    List<SupplementInventory> getUserInventory(ListUserInventoryQuery query);

    record ListUserInventoryQuery(UserId userId) {
    }
}
