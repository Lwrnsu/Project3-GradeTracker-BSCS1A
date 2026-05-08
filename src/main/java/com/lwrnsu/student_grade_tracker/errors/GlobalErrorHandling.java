package com.lwrnsu.student_grade_tracker.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lwrnsu.student_grade_tracker.api.ApiResponse;

@RestControllerAdvice
public class GlobalErrorHandling {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException err) {
        String message = err.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return new ApiResponse(false, message, null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DatabaseConnectionException.class)
    public ApiResponse handleDatabaseConnectionError(DatabaseConnectionException err) {
        return new ApiResponse(false, err.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ApiResponse handleDatabaseConnectionError(DataNotFoundException err) {
        return new ApiResponse(false, err.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ApiResponse handleInvalidCredentialsException(InvalidCredentialsException err) {
        return new ApiResponse(false, err.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataAlreadyExistException.class)
    public ApiResponse handleUserAlreadyExistException(DataAlreadyExistException err) {
        return new ApiResponse(false, err.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InsertingNullException.class)
    public ApiResponse handleInsertingNullException(InsertingNullException err) {
        return new ApiResponse(false, err.getMessage(), null);
    }
}
