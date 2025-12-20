package com.BmoGlitchCode.supplemint.application.usecase.supplement;

import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.ListUserSupplementsUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListUserSupplementsUseCaseImpl implements ListUserSupplementsUseCase {

    private final SupplementRepository supplementRepository;

    @Override
    public List<Supplement> list(ListUserSupplementsQuery query) {
        UserId userId = query.userId();
        if (query.activeOnly()) {
            return supplementRepository.findByUserIdAndActive(userId, true);
        } else {
            return supplementRepository.findByUserId(userId);
        }
    }
}
