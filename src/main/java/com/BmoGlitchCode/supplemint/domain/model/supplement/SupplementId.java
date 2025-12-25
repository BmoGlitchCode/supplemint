package com.BmoGlitchCode.supplemint.domain.model.supplement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing a unique supplement identifier.
 * Immutable and framework-agnostic.
 */
@Getter
@EqualsAndHashCode
@ToString
public class SupplementId {

    private final UUID value;

    public SupplementId(UUID value) {
        this.value = Objects.requireNonNull(value, "SupplementId value cannot be null");
    }

    public static SupplementId of(UUID value) {
        return new SupplementId(value);
    }

    public static SupplementId generate() {
        return new SupplementId(UUID.randomUUID());
    }

    public static SupplementId fromString(String value) {
        return new SupplementId(UUID.fromString(value));
    }
}
