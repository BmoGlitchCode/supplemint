package com.BmoGlitchCode.supplemint.domain.model.supplement;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the physical form or delivery method of the supplement.
 * distinct from the measurement unit.
 */
@Getter
@AllArgsConstructor
public enum DosageType {

    // Solid forms
    SOFTGEL("Softgel"),
    PILL("Pill"),
    GUMMY("Gummy"),
    LOZENGE("Lozenge"),
    CHEWABLE("Chewable"),

    // Loose/Volume forms
    SCOOP("Scoop"),
    DROP("Drop"),
    SPRAY("Spray"),

    // Other
    SERVING("Serving");

    private final String displayName;
}
