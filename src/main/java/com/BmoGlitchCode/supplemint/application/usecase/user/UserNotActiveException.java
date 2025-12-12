package com.BmoGlitchCode.supplemint.application.usecase.user;

/**
 * Exception thrown when a deactivated user attempts to login.
 */
public class UserNotActiveException extends RuntimeException {

    public UserNotActiveException(String message) {
        super(message);
    }
}
