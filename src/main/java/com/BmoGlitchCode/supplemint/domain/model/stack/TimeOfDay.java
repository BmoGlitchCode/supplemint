package com.BmoGlitchCode.supplemint.domain.model.stack;

/**
 * Enum representing the time of day for supplement consumption.
 */
public enum TimeOfDay {
    MORNING,
    AFTERNOON,
    EVENING,
    NIGHT,
    ANYTIME;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
