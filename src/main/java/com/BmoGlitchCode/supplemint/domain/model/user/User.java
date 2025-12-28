package com.BmoGlitchCode.supplemint.domain.model.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain Entity representing a User for authentication purposes.
 * This is a pure domain object with no framework dependencies.
 * 
 * Used for login and registration flows.
 */
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "passwordHash")
public class User {

    @EqualsAndHashCode.Include
    private final UserId id;

    private Email email;
    private String passwordHash;
    private String firstName;
    private String lastName;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    // Factory method for creating a new user during registration
    public static User of(Email email, String passwordHash, String firstName, String lastName) {
        Instant now = Instant.now();
        return User.builder()
                .id(UserId.generate())
                .email(email)
                .passwordHash(passwordHash)
                .firstName(firstName)
                .lastName(lastName)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public String getFullName() {
        if (firstName == null && lastName == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (firstName != null) {
            sb.append(firstName);
        }
        if (lastName != null) {
            if (!sb.isEmpty()) {
                sb.append(" ");
            }
            sb.append(lastName);
        }
        return sb.toString();
    }

    // Domain behavior methods
    public void updateEmail(Email newEmail) {
        this.email = Objects.requireNonNull(newEmail, "Email cannot be null");
        this.updatedAt = Instant.now();
    }

    public void updatePassword(String newPasswordHash) {
        this.passwordHash = Objects.requireNonNull(newPasswordHash, "Password hash cannot be null");
        this.updatedAt = Instant.now();
    }

    public void updateProfile(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
}
