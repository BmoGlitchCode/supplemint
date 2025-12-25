package com.BmoGlitchCode.supplemint.domain.port.input.inventory;

import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AddInventoryUseCase {

    SupplementInventory addInventory(AddInventoryCommand command);

    record AddInventoryCommand(
            UserId userId,
            SupplementId supplementId,
            LocalDate purchaseDate,
            BigDecimal totalUnits,
            DosageType unitType,
            Integer labelServings,
            BigDecimal unitsPerServing,
            BigDecimal dosagePerServing,
            DosageUnit dosageUnit,
            LocalDate expirationDate,
            BigDecimal cost,
            String vendor,
            String batchNumber,
            String notes) {
    }
}
