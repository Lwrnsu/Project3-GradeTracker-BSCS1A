package com.lwrnsu.student_grade_tracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private int totalStudents;
    private int totalSubjects;
    private int totalPassing;
    private int totalFailing;
}
