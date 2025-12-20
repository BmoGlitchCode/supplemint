package com.BmoGlitchCode.supplemint.domain.model.supplement;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing measurement units for supplement dosage.
 * Examples: Weight (mg, g), Volume (ml, l).
 */
@Getter
@AllArgsConstructor
public enum DosageUnit {
    // Weight units
    MG("mg", "Milligrams"),
    G("g", "Grams"),
    MCG("mcg", "Micrograms"),
    KG("kg", "Kilograms"),

    // Volume units
    ML("ml", "Milliliters"),
    L("l", "Liters"),
    FL_OZ("fl oz", "Fluid Ounces"),
    TSP("tsp", "Teaspoon"),
    TBSP("tbsp", "Tablespoon"),

    // International Units
    IU("IU", "International Units");

    private final String abbreviation;
    private final String displayName;

    public static DosageUnit fromAbbreviation(String abbreviation) {
        for (DosageUnit unit : values()) {
            if (unit.abbreviation.equalsIgnoreCase(abbreviation)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown dosage unit abbreviation: " + abbreviation);
    }
}
