package com.BmoGlitchCode.supplemint.application.usecase.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.DeleteSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSupplementUseCaseImpl implements DeleteSupplementUseCase {

    private final SupplementRepository supplementRepository;

    @Override
    public void delete(DeleteSupplementCommand command) {
        SupplementId id = command.supplementId();
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplement not found with ID: " + id.getValue()));

        if (!supplement.belongsToUser(command.userId())) {
            throw new IllegalArgumentException("Supplement does not belong to user");
        }

        supplement.deactivate();
        supplementRepository.save(supplement);
    }
}
