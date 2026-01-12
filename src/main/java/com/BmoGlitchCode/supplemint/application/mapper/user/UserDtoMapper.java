package com.BmoGlitchCode.supplemint.application.mapper.user;

import com.BmoGlitchCode.supplemint.application.dto.request.user.LoginUserRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.user.RegisterUserRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.user.UserResponse;
import com.BmoGlitchCode.supplemint.domain.model.user.User;
import com.BmoGlitchCode.supplemint.domain.port.input.user.LoginUserUseCase;
import com.BmoGlitchCode.supplemint.domain.port.input.user.RegisterUserUseCase;

/**
 * Mapper for converting between User DTOs and domain objects/commands.
 */
public class UserDtoMapper {

    private UserDtoMapper() {
        // Utility class
    }

    /**
     * Maps a RegisterUserRequest to a RegisterCommand.
     */
    public static RegisterUserUseCase.RegisterCommand toRegisterCommand(RegisterUserRequest request) {
        return new RegisterUserUseCase.RegisterCommand(
                request.email(),
                request.password(),
                request.firstName(),
                request.lastName());
    }

    /**
     * Maps a LoginUserRequest to a LoginCommand.
     */
    public static LoginUserUseCase.LoginCommand toLoginCommand(LoginUserRequest request) {
        return new LoginUserUseCase.LoginCommand(
                request.email(),
                request.password());
    }

    /**
     * Maps a User domain object to a UserResponse DTO.
     */
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId().getValue().toString(),
                user.getEmail().getValue(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
