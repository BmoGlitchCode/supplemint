package com.BmoGlitchCode.supplemint.domain.model.supplement;

import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Domain Entity representing a Supplement.
 * This is a pure domain object with no framework dependencies.
 * 
 * Represents a supplement that a user tracks, including basic info,
 * dosage configuration, and metadata.
 */
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Supplement {

    @EqualsAndHashCode.Include
    private final SupplementId id;

    private final UserId userId;

    private String name;
    private String description;
    private String brand;

    // Configuration for the dosage
    private DosageType dosageType; // e.g. Capsule, Tablet, Scoop
    private BigDecimal defaultDosageAmount; // e.g. 500, 1
    private DosageUnit defaultDosageUnit; // e.g. mg, g. Nullable if the unit is implied by the type (e.g. 1 Tablet)

    private String notes;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    /**
     * Factory method for creating a new supplement.
     */
    public static Supplement createNew(
            UserId userId,
            String name,
            String description,
            String brand,
            DosageType dosageType,
            BigDecimal defaultDosageAmount,
            DosageUnit defaultDosageUnit,
            String notes) {

        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(name, "Supplement name cannot be null");

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplement name cannot be empty");
        }

        Instant now = Instant.now();
        return Supplement.builder()
                .id(SupplementId.generate())
                .userId(userId)
                .name(name.trim())
                .description(description)
                .brand(brand)
                .dosageType(dosageType)
                .defaultDosageAmount(defaultDosageAmount)
                .defaultDosageUnit(defaultDosageUnit)
                .notes(notes)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    // Domain behavior methods maintained for specific validation and timestamp
    // updates

    /**
     * Updates the basic information of the supplement.
     */
    public void updateBasicInfo(String name, String description, String brand) {
        Objects.requireNonNull(name, "Supplement name cannot be null");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplement name cannot be empty");
        }

        this.name = name.trim();
        this.description = description;
        this.brand = brand;
        this.updatedAt = Instant.now();
    }

    /**
     * Updates the dosage configuration.
     */
    public void updateDosageConfig(DosageType type, BigDecimal defaultAmount, DosageUnit defaultUnit) {
        if (defaultAmount != null && defaultAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Default dosage amount must be positive");
        }

        this.dosageType = type;
        this.defaultDosageAmount = defaultAmount;
        this.defaultDosageUnit = defaultUnit;
        this.updatedAt = Instant.now();
    }

    /**
     * Updates the notes field.
     */
    public void updateNotes(String notes) {
        this.notes = notes;
        this.updatedAt = Instant.now();
    }

    /**
     * Deactivates the supplement (soft delete).
     */
    public void deactivate() {
        this.active = false;
        this.updatedAt = Instant.now();
    }

    /**
     * Reactivates the supplement.
     */
    public void activate() {
        this.active = true;
        this.updatedAt = Instant.now();
    }

    /**
     * Checks if the supplement belongs to the specified user.
     */
    public boolean belongsToUser(UserId userId) {
        return this.userId.equals(userId);
    }
}
