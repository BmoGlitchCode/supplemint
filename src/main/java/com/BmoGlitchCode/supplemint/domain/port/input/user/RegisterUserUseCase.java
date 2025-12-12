package com.BmoGlitchCode.supplemint.domain.port.input.user;

import com.BmoGlitchCode.supplemint.domain.model.user.User;

/**
 * Input Port for user registration use case.
 * Defines the contract for registering new users in the system.
 */
public interface RegisterUserUseCase {

    /**
     * Registers a new user in the system.
     *
     * @param command the registration command containing user details
     * @return the created user
     * @throws UserAlreadyExistsException if a user with the same email already
     *                                    exists
     */
    User register(RegisterCommand command);

    /**
     * Command object containing data required for user registration.
     */
    record RegisterCommand(
            String email,
            String password,
            String firstName,
            String lastName) {
    }
}
