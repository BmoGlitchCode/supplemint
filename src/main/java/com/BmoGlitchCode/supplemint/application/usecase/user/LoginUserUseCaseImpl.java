package com.BmoGlitchCode.supplemint.application.usecase.user;

import com.BmoGlitchCode.supplemint.domain.model.user.Email;
import com.BmoGlitchCode.supplemint.domain.model.user.User;
import com.BmoGlitchCode.supplemint.domain.port.input.user.LoginUserUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.user.PasswordEncoder;
import com.BmoGlitchCode.supplemint.domain.port.output.user.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Application service implementing the user login use case.
 * Orchestrates domain objects and output ports to authenticate users.
 */
@RequiredArgsConstructor
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(LoginCommand command) {
        Email email = Email.of(command.email());

        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Check if account is active
        if (!user.isActive()) {
            throw new UserNotActiveException("User account is deactivated");
        }

        return user;
    }
}
