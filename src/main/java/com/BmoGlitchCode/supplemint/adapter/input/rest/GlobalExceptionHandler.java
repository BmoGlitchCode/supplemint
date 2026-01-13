package com.BmoGlitchCode.supplemint.adapter.input.rest;

import com.BmoGlitchCode.supplemint.application.usecase.stack.StackAccessDeniedException;
import com.BmoGlitchCode.supplemint.application.usecase.stack.StackNotFoundException;
import com.BmoGlitchCode.supplemint.application.usecase.supplement.SupplementAccessDeniedException;
import com.BmoGlitchCode.supplemint.application.usecase.supplement.SupplementNotFoundException;
import com.BmoGlitchCode.supplemint.application.usecase.supplementlog.SupplementLogAccessDeniedException;
import com.BmoGlitchCode.supplemint.application.usecase.supplementlog.SupplementLogNotFoundException;
import com.BmoGlitchCode.supplemint.application.usecase.user.InvalidCredentialsException;
import com.BmoGlitchCode.supplemint.application.usecase.user.UserAlreadyExistsException;
import com.BmoGlitchCode.supplemint.application.usecase.user.UserNotActiveException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST controllers.
 * Converts exceptions to appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("USER_ALREADY_EXISTS", ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("INVALID_CREDENTIALS", ex.getMessage()));
    }

    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleUserNotActive(UserNotActiveException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("USER_NOT_ACTIVE", ex.getMessage()));
    }

    @ExceptionHandler(SupplementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplementNotFound(SupplementNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("SUPPLEMENT_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(SupplementAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleSupplementAccessDenied(SupplementAccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("SUPPLEMENT_ACCESS_DENIED", ex.getMessage()));
    }

    @ExceptionHandler(StackNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStackNotFound(StackNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("STACK_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(StackAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleStackAccessDenied(StackAccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("STACK_ACCESS_DENIED", ex.getMessage()));
    }

    @ExceptionHandler(SupplementLogNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplementLogNotFound(SupplementLogNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("SUPPLEMENT_LOG_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(SupplementLogAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleSupplementLogAccessDenied(SupplementLogAccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("SUPPLEMENT_LOG_ACCESS_DENIED", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_INPUT", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponse("VALIDATION_FAILED", "Validation failed", errors));
    }

    /**
     * Standard error response structure.
     */
    public record ErrorResponse(
            String code,
            String message,
            Instant timestamp) {
        public ErrorResponse(String code, String message) {
            this(code, message, Instant.now());
        }
    }

    /**
     * Validation error response with field-level errors.
     */
    public record ValidationErrorResponse(
            String code,
            String message,
            Map<String, String> errors,
            Instant timestamp) {
        public ValidationErrorResponse(String code, String message, Map<String, String> errors) {
            this(code, message, errors, Instant.now());
        }
    }
}
