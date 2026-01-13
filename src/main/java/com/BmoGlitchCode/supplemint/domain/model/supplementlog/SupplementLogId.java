package com.BmoGlitchCode.supplemint.domain.model.supplementlog;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing a unique supplement log identifier.
 * Immutable and framework-agnostic.
 */
@Getter
@EqualsAndHashCode
@ToString
public class SupplementLogId {

    private final UUID value;

    public SupplementLogId(UUID value) {
        this.value = Objects.requireNonNull(value, "SupplementLogId value cannot be null");
    }

    public static SupplementLogId of(UUID value) {
        return new SupplementLogId(value);
    }

    public static SupplementLogId generate() {
        return new SupplementLogId(UUID.randomUUID());
    }

    public static SupplementLogId fromString(String value) {
        return new SupplementLogId(UUID.fromString(value));
    }
}
