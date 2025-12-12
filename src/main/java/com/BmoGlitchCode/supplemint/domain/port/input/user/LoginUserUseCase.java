package com.BmoGlitchCode.supplemint.domain.port.input.user;

import com.BmoGlitchCode.supplemint.domain.model.user.User;

/**
 * Input Port for user login/authentication use case.
 * Defines the contract for authenticating users.
 */
public interface LoginUserUseCase {

    /**
     * Authenticates a user with email and password.
     *
     * @param command the login command containing credentials
     * @return the authenticated user
     * @throws InvalidCredentialsException if credentials are invalid
     * @throws UserNotActiveException      if the user account is deactivated
     */
    User login(LoginCommand command);

    /**
     * Command object containing login credentials.
     */
    record LoginCommand(
            String email,
            String password) {
    }
}
