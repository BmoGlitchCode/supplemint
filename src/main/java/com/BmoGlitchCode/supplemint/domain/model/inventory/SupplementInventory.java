package com.BmoGlitchCode.supplemint.domain.model.inventory;

import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageType;
import com.BmoGlitchCode.supplemint.domain.model.supplement.DosageUnit;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SupplementInventory {

    @EqualsAndHashCode.Include
    private final SupplementInventoryId id;

    @NonNull
    private final UserId userId;

    @NonNull
    private final SupplementId supplementId;

    @Builder.Default
    private LocalDate purchaseDate = LocalDate.now();

    // Physical inventory
    @NonNull
    private BigDecimal totalUnits; // total capsules/tablets/scoops when bought
    @NonNull
    private BigDecimal remainingUnits; // currently remaining
    @NonNull
    private DosageType unitType; // capsule, tablet, etc.

    // Label information
    private Integer labelServings; // e.g., 30 servings
    @Builder.Default
    private BigDecimal unitsPerServing = BigDecimal.ONE; // e.g., 2 capsules per serving
    private BigDecimal dosagePerServing; // e.g., 500
    private DosageUnit dosageUnit; // e.g., mg

    // Metadata
    private LocalDate expirationDate;
    private BigDecimal cost;
    private String vendor;
    private String batchNumber;
    private String notes;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private Instant createdAt = Instant.now();
    private Instant updatedAt;

    public static SupplementInventory createNew(
            UserId userId,
            SupplementId supplementId,
            LocalDate purchaseDate,
            BigDecimal totalUnits,
            DosageType unitType,
            Integer labelServings,
            BigDecimal unitsPerServing,
            BigDecimal dosagePerServing,
            DosageUnit dosageUnit,
            LocalDate expirationDate,
            BigDecimal cost,
            String vendor,
            String batchNumber,
            String notes) {

        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(supplementId, "SupplementId cannot be null");
        Objects.requireNonNull(totalUnits, "Total units cannot be null");
        Objects.requireNonNull(unitType, "Unit type cannot be null");

        if (totalUnits.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total units must be positive");
        }

        Instant now = Instant.now();
        return SupplementInventory.builder()
                .id(SupplementInventoryId.generate())
                .userId(userId)
                .supplementId(supplementId)
                .purchaseDate(purchaseDate != null ? purchaseDate : LocalDate.now())
                .totalUnits(totalUnits)
                .remainingUnits(totalUnits) // Start with full bottle
                .unitType(unitType)
                .labelServings(labelServings)
                .unitsPerServing(unitsPerServing != null ? unitsPerServing : BigDecimal.ONE)
                .dosagePerServing(dosagePerServing)
                .dosageUnit(dosageUnit)
                .expirationDate(expirationDate)
                .cost(cost)
                .vendor(vendor)
                .batchNumber(batchNumber)
                .notes(notes)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void updateRemainingUnits(BigDecimal newAmount) {
        if (newAmount == null) {
            throw new IllegalArgumentException("New amount cannot be empty");
        }
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Remaining units cannot be negative");
        }
        if (newAmount.compareTo(totalUnits) > 0) {
            throw new IllegalArgumentException("Remaining units cannot be greater than total units");
        }

        this.remainingUnits = newAmount;
        this.updatedAt = Instant.now();

        if (this.remainingUnits.compareTo(BigDecimal.ZERO) == 0) {
            this.active = false;
        }
    }

    public void markAsEmpty() {
        this.remainingUnits = BigDecimal.ZERO;
        this.active = false;
        this.updatedAt = Instant.now();
    }
}
