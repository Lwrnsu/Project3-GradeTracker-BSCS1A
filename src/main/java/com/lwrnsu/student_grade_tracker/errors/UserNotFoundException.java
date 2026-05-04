package com.lwrnsu.student_grade_tracker.errors;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
