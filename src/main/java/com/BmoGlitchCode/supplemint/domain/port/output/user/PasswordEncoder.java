package com.BmoGlitchCode.supplemint.domain.port.output.user;

/**
 * Output Port for password encoding operations.
 * This interface abstracts the password hashing mechanism,
 * allowing the domain to remain agnostic of the specific implementation.
 */
public interface PasswordEncoder {

    /**
     * Encodes a raw password into a secure hash.
     *
     * @param rawPassword the plain text password
     * @return the encoded password hash
     */
    String encode(String rawPassword);

    /**
     * Verifies that a raw password matches an encoded password.
     *
     * @param rawPassword     the plain text password to check
     * @param encodedPassword the encoded password hash to compare against
     * @return true if the passwords match, false otherwise
     */
    boolean matches(String rawPassword, String encodedPassword);
}
