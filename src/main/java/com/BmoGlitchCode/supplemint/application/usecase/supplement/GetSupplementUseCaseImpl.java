package com.BmoGlitchCode.supplemint.application.usecase.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.GetSupplementUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSupplementUseCaseImpl implements GetSupplementUseCase {

    private final SupplementRepository supplementRepository;

    @Override
    public Supplement get(GetSupplementQuery query) {
        SupplementId id = query.supplementId();
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new SupplementNotFoundException(id));

        if (!supplement.belongsToUser(query.userId())) {
            throw new SupplementAccessDeniedException(id, query.userId());
        }

        return supplement;
    }
}
