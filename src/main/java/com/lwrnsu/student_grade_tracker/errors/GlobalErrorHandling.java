package com.lwrnsu.student_grade_tracker.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lwrnsu.student_grade_tracker.api.ApiResponse;

@RestControllerAdvice
public class GlobalErrorHandling {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DatabaseConnectionException.class)
    public ApiResponse handleDatabaseConnectionError(DatabaseConnectionException err) {
        return new ApiResponse(false, "Database Connection not found.", null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse handleDatabaseConnectionError(UserNotFoundException err) {
        return new ApiResponse(false, "User not found.", null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ApiResponse handleInvalidCredentialsException(InvalidCredentialsException err) {
        return new ApiResponse(false, "Invalid Credentials.", null);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistException.class)
    public ApiResponse handleUserAlreadyExistException(UserAlreadyExistException err) {
        return new ApiResponse(false, "Username already exist.", null);
    }
}
