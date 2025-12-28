package com.BmoGlitchCode.supplemint.domain.model.stack;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value Object for Stack ID.
 */
public record StackId(UUID value) implements Serializable {
    public StackId {
        Objects.requireNonNull(value, "StackId value cannot be null");
    }

    public static StackId of(UUID value) {
        return new StackId(value);
    }

    public static StackId generate() {
        return new StackId(UUID.randomUUID());
    }

    public static StackId fromString(String value) {
        return new StackId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
