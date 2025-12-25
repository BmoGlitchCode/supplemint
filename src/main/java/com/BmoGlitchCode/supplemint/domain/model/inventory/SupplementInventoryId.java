package com.BmoGlitchCode.supplemint.domain.model.inventory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class SupplementInventoryId {

    private final UUID value;

    public SupplementInventoryId(@NonNull UUID value) {
        this.value = value;
    }

    public static SupplementInventoryId generate() {
        return new SupplementInventoryId(UUID.randomUUID());
    }

    public static SupplementInventoryId fromString(String uuid) {
        return new SupplementInventoryId(UUID.fromString(uuid));
    }
}
