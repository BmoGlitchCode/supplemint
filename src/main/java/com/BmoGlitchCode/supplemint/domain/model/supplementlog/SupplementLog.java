package com.BmoGlitchCode.supplemint.domain.model.supplementlog;

import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.stack.StackId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Domain Entity representing a Supplement Log entry.
 * This is a pure domain object with no framework dependencies.
 *
 * Represents a record of a user taking (or skipping) a supplement dose.
 * Log entries are generally immutable after creation, except for notes
 * and skipped status which can be updated.
 *
 * Note: Dosage information (amount and unit) is derived from the Supplement entity,
 * not stored in the log. The log only tracks how many units were taken.
 */
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class SupplementLog {

    @EqualsAndHashCode.Include
    private final SupplementLogId id;

    private final UserId userId;
    private final SupplementId supplementId;
    private final StackId stackId; // nullable - optional association with a stack

    private final Instant takenAt;
    private final BigDecimal unitsTaken; // how many pills/scoops/etc.

    private boolean skipped;
    private String notes;

    @Builder.Default
    private final Instant createdAt = Instant.now();

    /**
     * Factory method for creating a new supplement log entry for an actual intake.
     *
     * @param userId       the user creating the log
     * @param supplementId the supplement being logged
     * @param stackId      optional - the stack this log is associated with (null if standalone)
     * @param takenAt      when the supplement was taken
     * @param unitsTaken   number of units taken (e.g., pills, scoops)
     * @param notes        optional notes about the intake
     * @return a new SupplementLog instance
     */
    public static SupplementLog of(
            UserId userId,
            SupplementId supplementId,
            StackId stackId,
            Instant takenAt,
            BigDecimal unitsTaken,
            String notes) {

        validateRequiredFields(userId, supplementId, takenAt, unitsTaken);
        validatePositiveUnits(unitsTaken);

        return SupplementLog.builder()
                .id(SupplementLogId.generate())
                .userId(userId)
                .supplementId(supplementId)
                .stackId(stackId)
                .takenAt(takenAt)
                .unitsTaken(unitsTaken)
                .skipped(false)
                .notes(notes)
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Factory method for creating a skipped log entry.
     * A skipped entry still records when it was supposed to be taken and the planned dosage.
     *
     * @param userId       the user creating the log
     * @param supplementId the supplement being logged
     * @param stackId      optional - the stack this log is associated with (null if standalone)
     * @param scheduledAt  when the supplement was scheduled to be taken
     * @param unitsTaken   number of units that were planned to be taken
     * @param notes        optional notes about why the dose was skipped
     * @return a new SupplementLog instance marked as skipped
     */
    public static SupplementLog ofSkipped(
            UserId userId,
            SupplementId supplementId,
            StackId stackId,
            Instant scheduledAt,
            BigDecimal unitsTaken,
            String notes) {

        validateRequiredFields(userId, supplementId, scheduledAt, unitsTaken);
        validatePositiveUnits(unitsTaken);

        return SupplementLog.builder()
                .id(SupplementLogId.generate())
                .userId(userId)
                .supplementId(supplementId)
                .stackId(stackId)
                .takenAt(scheduledAt)
                .unitsTaken(unitsTaken)
                .skipped(true)
                .notes(notes)
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Marks this log entry as skipped.
     * Can be used to update an entry that was mistakenly logged as taken.
     */
    public void markAsSkipped() {
        this.skipped = true;
    }

    /**
     * Marks this log entry as taken (not skipped).
     * Can be used to correct a log that was mistakenly marked as skipped.
     */
    public void markAsTaken() {
        this.skipped = false;
    }

    /**
     * Updates the notes for this log entry.
     *
     * @param notes the new notes (can be null to clear)
     */
    public void updateNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Checks if this log entry belongs to the specified user.
     *
     * @param userId the user ID to check
     * @return true if this log belongs to the user
     */
    public boolean belongsToUser(UserId userId) {
        return this.userId.equals(userId);
    }

    /**
     * Checks if this log is associated with a specific stack.
     *
     * @return true if this log has a stack association
     */
    public boolean hasStackAssociation() {
        return this.stackId != null;
    }

    /**
     * Checks if this log is for a specific supplement.
     *
     * @param supplementId the supplement ID to check
     * @return true if this log is for the specified supplement
     */
    public boolean isForSupplement(SupplementId supplementId) {
        return this.supplementId.equals(supplementId);
    }

    private static void validateRequiredFields(
            UserId userId,
            SupplementId supplementId,
            Instant takenAt,
            BigDecimal unitsTaken) {

        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(supplementId, "SupplementId cannot be null");
        Objects.requireNonNull(takenAt, "TakenAt timestamp cannot be null");
        Objects.requireNonNull(unitsTaken, "UnitsTaken cannot be null");
    }

    private static void validatePositiveUnits(BigDecimal unitsTaken) {
        if (unitsTaken.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Units taken must be positive");
        }
    }
}
