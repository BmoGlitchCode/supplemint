package com.BmoGlitchCode.supplemint.application.usecase.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.UpdateSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSupplementUseCaseImpl implements UpdateSupplementUseCase {

    private final SupplementRepository supplementRepository;

    @Override
    public Supplement update(UpdateSupplementCommand command) {
        SupplementId id = command.supplementId();
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplement not found with ID: " + id.getValue()));

        if (!supplement.belongsToUser(command.userId())) {
            throw new IllegalArgumentException("Supplement does not belong to user");
        }

        supplement.updateBasicInfo(command.name(), command.description(), command.brand());
        supplement.updateDosageConfig(command.dosageType(), command.dosagePerServing(), command.dosageUnit(),
                command.servingSize());
        supplement.updateNotes(command.notes());

        if (command.remainingUnits() != null) {
            supplement.updateRemainingUnits(command.remainingUnits());
        }

        return supplementRepository.save(supplement);
    }
}
