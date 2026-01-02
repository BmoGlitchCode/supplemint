package com.BmoGlitchCode.supplemint.domain.model.stack;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Value object or local entity representing a supplement within a stack.
 */
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class StackItem {

    /**
     * The ID of the supplement included in the stack.
     */
    @NonNull
    private final SupplementId supplementId;

    /**
     * Order for display purposes.
     */
    @Builder.Default
    private int sortOrder = 0;

    public static StackItem of(SupplementId supplementId, int sortOrder) {
        return StackItem.builder()
                .supplementId(supplementId)
                .sortOrder(sortOrder)
                .build();
    }
}
