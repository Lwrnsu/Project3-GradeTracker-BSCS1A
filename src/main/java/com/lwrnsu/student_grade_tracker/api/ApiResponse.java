package com.lwrnsu.student_grade_tracker.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
}
