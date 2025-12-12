package com.BmoGlitchCode.supplemint.domain.port.output.user;

import com.BmoGlitchCode.supplemint.domain.model.user.Email;
import com.BmoGlitchCode.supplemint.domain.model.user.User;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;

import java.util.Optional;

/**
 * Output Port for User persistence operations.
 * This interface is implemented by adapters (e.g., JPA repository adapter)
 * and used by the application layer to persist and retrieve users.
 */
public interface UserRepository {

    /**
     * Saves a user to the repository.
     *
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the user ID
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findById(UserId id);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(Email email);

    /**
     * Checks if a user with the given email already exists.
     *
     * @param email the email to check
     * @return true if a user with this email exists, false otherwise
     */
    boolean existsByEmail(Email email);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the user ID
     */
    void deleteById(UserId id);
}
