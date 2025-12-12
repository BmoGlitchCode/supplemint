package com.BmoGlitchCode.supplemint.adapter.input.rest.user;

import com.BmoGlitchCode.supplemint.application.dto.request.user.LoginUserRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.user.RegisterUserRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.user.UserResponse;
import com.BmoGlitchCode.supplemint.application.mapper.user.UserDtoMapper;
import com.BmoGlitchCode.supplemint.domain.model.user.User;
import com.BmoGlitchCode.supplemint.domain.port.input.user.LoginUserUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.user.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for user authentication operations.
 * Handles registration and login endpoints.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    /**
     * Register a new user.
     * 
     * @param request registration details
     * @return the created user
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserUseCase.RegisterCommand command = UserDtoMapper.toRegisterCommand(request);
        User user = registerUserUseCase.register(command);
        UserResponse response = UserDtoMapper.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login an existing user.
     * 
     * @param request login credentials
     * @return the authenticated user
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginUserRequest request) {
        LoginUserUseCase.LoginCommand command = UserDtoMapper.toLoginCommand(request);
        User user = loginUserUseCase.login(command);
        UserResponse response = UserDtoMapper.toUserResponse(user);
        return ResponseEntity.ok(response);
    }
}
