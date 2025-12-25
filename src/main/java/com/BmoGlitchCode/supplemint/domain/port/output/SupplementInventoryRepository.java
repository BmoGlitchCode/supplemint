package com.BmoGlitchCode.supplemint.domain.port.output;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventoryId;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.List;
import java.util.Optional;

public interface SupplementInventoryRepository {

    SupplementInventory save(SupplementInventory inventory);

    Optional<SupplementInventory> findById(SupplementInventoryId id);

    List<SupplementInventory> findByUserId(UserId userId);

    List<SupplementInventory> findBySupplementId(SupplementId supplementId);
}
