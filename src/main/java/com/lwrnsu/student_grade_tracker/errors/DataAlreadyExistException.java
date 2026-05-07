package com.lwrnsu.student_grade_tracker.errors;

public class DataAlreadyExistException extends RuntimeException{
    public DataAlreadyExistException(String message) {
        super(message);
    }
}
