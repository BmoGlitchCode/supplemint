package com.BmoGlitchCode.supplemint.application.usecase.user;

import com.BmoGlitchCode.supplemint.domain.model.user.Email;
import com.BmoGlitchCode.supplemint.domain.model.user.User;
import com.BmoGlitchCode.supplemint.domain.port.input.user.RegisterUserUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.user.PasswordEncoder;
import com.BmoGlitchCode.supplemint.domain.port.output.user.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Application service implementing the user registration use case.
 * Orchestrates domain objects and output ports to fulfill the use case.
 */
@RequiredArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterCommand command) {
        Email email = Email.of(command.email());

        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        // Hash password
        String passwordHash = passwordEncoder.encode(command.password());

        // Create domain user
        User user = User.of(
                email,
                passwordHash,
                command.firstName(),
                command.lastName());

        // Persist and return
        return userRepository.save(user);
    }
}
