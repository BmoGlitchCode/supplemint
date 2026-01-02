package com.BmoGlitchCode.supplemint.application.usecase.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.CreateSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateSupplementUseCaseImpl implements CreateSupplementUseCase {

    private final SupplementRepository supplementRepository;

    @Override
    public Supplement create(CreateSupplementCommand command) {
        Supplement supplement = Supplement.of(
                command.userId(),
                command.name(),
                command.description(),
                command.brand(),
                command.dosageType(),
                command.dosagePerServing(),
                command.dosageUnit(),
                command.servingSize(),
                command.totalUnits(),
                command.remainingUnits(),
                command.notes());

        return supplementRepository.save(supplement);
    }
}
