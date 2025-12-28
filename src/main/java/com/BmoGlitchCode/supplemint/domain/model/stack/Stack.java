package com.BmoGlitchCode.supplemint.domain.model.stack;

import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate Root representing a Stack (group of supplements).
 */
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Stack {

    @EqualsAndHashCode.Include
    private final StackId id;

    @NonNull
    private final UserId userId;

    private String name;
    private String description;
    private TimeOfDay defaultTime;
    private String color;

    @Builder.Default
    private List<StackItem> items = new ArrayList<>();

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    public static Stack of(
            UserId userId,
            String name,
            String description,
            TimeOfDay defaultTime,
            String color,
            List<StackItem> items) {

        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(name, "Stack name cannot be null");

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Stack name cannot be empty");
        }

        Instant now = Instant.now();
        return Stack.builder()
                .id(StackId.generate())
                .userId(userId)
                .name(name.trim())
                .description(description)
                .defaultTime(defaultTime)
                .color(color)
                .items(items != null ? new ArrayList<>(items) : new ArrayList<>())
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void updateInfo(String name, String description, TimeOfDay defaultTime, String color) {
        Objects.requireNonNull(name, "Stack name cannot be null");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Stack name cannot be empty");
        }

        this.name = name.trim();
        this.description = description;
        this.defaultTime = defaultTime;
        this.color = color;
        this.updatedAt = Instant.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = Instant.now();
    }

    public void activate() {
        this.active = true;
        this.updatedAt = Instant.now();
    }

    public void setItems(List<StackItem> items) {
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.updatedAt = Instant.now();
    }

    public void addItem(StackItem item) {
        if (item == null) {
            throw new IllegalArgumentException("StackItem cannot be null");
        }
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        boolean exists = this.items.stream()
                .anyMatch(i -> i.getSupplementId().equals(item.getSupplementId()));

        if (exists) {
            return;
        }

        this.items.add(item);
        this.updatedAt = Instant.now();
    }

    public void removeItem(com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId supplementId) {
        if (supplementId == null) {
            throw new IllegalArgumentException("SupplementId cannot be null");
        }
        if (this.items != null) {
            boolean removed = this.items.removeIf(item -> item.getSupplementId().equals(supplementId));
            if (removed) {
                this.updatedAt = Instant.now();
            }
        }
    }

    public List<StackItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
