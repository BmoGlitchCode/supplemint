package com.BmoGlitchCode.supplemint.application.mapper.inventory;

import com.BmoGlitchCode.supplemint.application.dto.request.inventory.AddInventoryRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.inventory.UpdateInventoryRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.inventory.SupplementInventoryResponse;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventory;
import com.BmoGlitchCode.supplemint.domain.model.inventory.SupplementInventoryId;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.AddInventoryUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.UpdateInventoryUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.ListUserInventoryUseCase;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupplementInventoryDtoMapper {

    public AddInventoryUseCase.AddInventoryCommand toAddCommand(AddInventoryRequest request) {
        return new AddInventoryUseCase.AddInventoryCommand(
                UserId.of(request.userId()),
                SupplementId.of(request.supplementId()), // Uses static factory method(),
                request.purchaseDate(),
                request.totalUnits(),
                request.unitType(),
                request.labelServings(),
                request.unitsPerServing(),
                request.dosagePerServing(),
                request.dosageUnit(),
                request.expirationDate(),
                request.cost(),
                request.vendor(),
                request.batchNumber(),
                request.notes());
    }

    public UpdateInventoryUseCase.UpdateInventoryCommand toUpdateCommand(UUID inventoryId,
            UpdateInventoryRequest request) {
        return new UpdateInventoryUseCase.UpdateInventoryCommand(
                UserId.of(request.userId()),
                new SupplementInventoryId(inventoryId),
                request.remainingUnits(),
                request.notes());
    }

    public ListUserInventoryUseCase.ListUserInventoryQuery toListQuery(UUID userId) {
        return new ListUserInventoryUseCase.ListUserInventoryQuery(UserId.of(userId));
    }

    public SupplementInventoryResponse toResponse(SupplementInventory inventory) {
        return new SupplementInventoryResponse(
                inventory.getId().getValue(),
                inventory.getUserId().getValue(),
                inventory.getSupplementId().getValue(),
                inventory.getPurchaseDate(),
                inventory.getTotalUnits(),
                inventory.getRemainingUnits(),
                inventory.getUnitType(),
                inventory.getLabelServings(),
                inventory.getUnitsPerServing(),
                inventory.getDosagePerServing(),
                inventory.getDosageUnit(),
                inventory.getExpirationDate(),
                inventory.getCost(),
                inventory.getVendor(),
                inventory.getBatchNumber(),
                inventory.getNotes(),
                inventory.isActive());
    }
}
