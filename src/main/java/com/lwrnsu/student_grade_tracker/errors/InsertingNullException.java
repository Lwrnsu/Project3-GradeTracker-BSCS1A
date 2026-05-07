package com.lwrnsu.student_grade_tracker.errors;

public class InsertingNullException extends RuntimeException{
    public InsertingNullException(String message) {
        super(message);
    }
}
