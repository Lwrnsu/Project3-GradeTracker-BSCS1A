package com.lwrnsu.student_grade_tracker.errors;

public class DatabaseConnectionException extends RuntimeException{
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
