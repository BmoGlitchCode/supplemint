package com.BmoGlitchCode.supplemint.application.mapper.supplement;

import com.BmoGlitchCode.supplemint.application.dto.request.supplement.CreateSupplementRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.supplement.UpdateSupplementRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.supplement.SupplementResponse;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.CreateSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.DeleteSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.GetSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.ListUserSupplementsUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.UpdateSupplementUseCase;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupplementDtoMapper {

    public SupplementResponse toResponse(Supplement supplement) {
        if (supplement == null) {
            return null;
        }
        return SupplementResponse.builder()
                .id(supplement.getId().getValue())
                .name(supplement.getName())
                .description(supplement.getDescription())
                .brand(supplement.getBrand())
                .dosageType(supplement.getDosageType())
                .dosagePerServing(supplement.getDosagePerServing())
                .dosageUnit(supplement.getDosageUnit())
                .servingSize(supplement.getServingSize())
                .notes(supplement.getNotes())
                .totalUnits(supplement.getTotalUnits())
                .remainingUnits(supplement.getRemainingUnits())
                .active(supplement.isActive())
                .createdAt(supplement.getCreatedAt())
                .updatedAt(supplement.getUpdatedAt())
                .build();
    }

    public CreateSupplementUseCase.CreateSupplementCommand toCreateCommand(CreateSupplementRequest request) {
        if (request == null) {
            return null;
        }
        return new CreateSupplementUseCase.CreateSupplementCommand(
                UserId.of(request.userId()),
                request.name(),
                request.description(),
                request.brand(),
                request.dosageType(),
                request.dosagePerServing(),
                request.dosageUnit(),
                request.servingSize(),
                request.totalUnits(),
                request.remainingUnits(),
                request.notes());
    }

    public UpdateSupplementUseCase.UpdateSupplementCommand toUpdateCommand(UUID id, UpdateSupplementRequest request) {
        if (request == null) {
            return null;
        }
        return new UpdateSupplementUseCase.UpdateSupplementCommand(
                UserId.of(request.userId()),
                SupplementId.of(id),
                request.name(),
                request.description(),
                request.brand(),
                request.dosageType(),
                request.dosagePerServing(),
                request.dosageUnit(),
                request.servingSize(),
                request.notes(),
                request.remainingUnits());
    }

    public GetSupplementUseCase.GetSupplementQuery toGetQuery(UUID userId, UUID supplementId) {
        return new GetSupplementUseCase.GetSupplementQuery(
                UserId.of(userId),
                SupplementId.of(supplementId));
    }

    public ListUserSupplementsUseCase.ListUserSupplementsQuery toListQuery(UUID userId, boolean activeOnly) {
        return new ListUserSupplementsUseCase.ListUserSupplementsQuery(
                UserId.of(userId),
                activeOnly);
    }

    public DeleteSupplementUseCase.DeleteSupplementCommand toDeleteCommand(UUID userId, UUID supplementId) {
        return new DeleteSupplementUseCase.DeleteSupplementCommand(
                UserId.of(userId),
                SupplementId.of(supplementId));
    }
}
