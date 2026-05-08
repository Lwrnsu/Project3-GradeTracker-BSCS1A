package com.lwrnsu.student_grade_tracker.errors;


public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
