package com.lwrnsu.student_grade_tracker.dto;

public class AuthResponse {
    private boolean success;

    public AuthResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
