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
    private DosageType dosageType; // e.g. Pill, Scoop
    private BigDecimal dosagePerServing; // e.g. 500, 1
    private DosageUnit dosageUnit; // e.g. mg, g.
    @Builder.Default
    private BigDecimal servingSize = BigDecimal.ONE; // e.g. 1 (pills), 2 (scoops)

    // Inventory Tracking
    @lombok.NonNull
    private final BigDecimal totalUnits; // total pills/scoops when bought
    @lombok.NonNull
    private BigDecimal remainingUnits; // currently remaining

    private String notes;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    /**
     * Factory method for creating a new supplement.
     *
     * @param remainingUnits Optional - if null, defaults to totalUnits
     */
    public static Supplement of(
            UserId userId,
            String name,
            String description,
            String brand,
            DosageType dosageType,
            BigDecimal dosagePerServing,
            DosageUnit dosageUnit,
            BigDecimal servingSize,
            BigDecimal totalUnits,
            BigDecimal remainingUnits,
            String notes) {

        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(name, "Supplement name cannot be null");
        Objects.requireNonNull(totalUnits, "Total units cannot be null");

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplement name cannot be empty");
        }
        if (totalUnits.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total units must be positive");
        }

        BigDecimal effectiveRemainingUnits = remainingUnits != null ? remainingUnits : totalUnits;
        if (effectiveRemainingUnits.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Remaining units cannot be negative");
        }
        if (effectiveRemainingUnits.compareTo(totalUnits) > 0) {
            throw new IllegalArgumentException("Remaining units cannot be greater than total units");
        }

        Instant now = Instant.now();
        return Supplement.builder()
                .id(SupplementId.generate())
                .userId(userId)
                .name(name.trim())
                .description(description)
                .brand(brand)
                .dosageType(dosageType)
                .dosagePerServing(dosagePerServing)
                .dosageUnit(dosageUnit)
                .servingSize(servingSize != null ? servingSize : BigDecimal.ONE)
                .totalUnits(totalUnits)
                .remainingUnits(effectiveRemainingUnits)
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
    public void updateDosageConfig(DosageType type, BigDecimal amount, DosageUnit unit, BigDecimal servingSize) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Dosage amount must be positive");
        }
        if (servingSize != null && servingSize.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Serving size must be positive");
        }

        this.dosageType = type;
        this.dosagePerServing = amount;
        this.dosageUnit = unit;
        this.servingSize = servingSize != null ? servingSize : BigDecimal.ONE;
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
     * Updates the remaining units.
     */
    public void updateRemainingUnits(BigDecimal remainingUnits) {
        if (remainingUnits != null && remainingUnits.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Remaining units cannot be negative");
        }
        if (remainingUnits != null && remainingUnits.compareTo(this.totalUnits) > 0) {
            throw new IllegalArgumentException("Remaining units cannot be greater than total units");
        }
        this.remainingUnits = remainingUnits;
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
