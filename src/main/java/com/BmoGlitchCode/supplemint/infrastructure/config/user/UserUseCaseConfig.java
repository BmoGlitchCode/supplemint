package com.BmoGlitchCode.supplemint.infrastructure.config.user;

import com.BmoGlitchCode.supplemint.application.usecase.user.LoginUserUseCaseImpl;
import com.BmoGlitchCode.supplemint.application.usecase.user.RegisterUserUseCaseImpl;
import com.BmoGlitchCode.supplemint.domain.port.input.user.LoginUserUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.user.RegisterUserUseCase;
import com.BmoGlitchCode.supplemint.domain.port.output.user.PasswordEncoder;
import com.BmoGlitchCode.supplemint.domain.port.output.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for wiring up user-related use cases with their
 * dependencies.
 */
@Configuration
public class UserUseCaseConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCaseImpl(userRepository, passwordEncoder);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return new LoginUserUseCaseImpl(userRepository, passwordEncoder);
    }
}
