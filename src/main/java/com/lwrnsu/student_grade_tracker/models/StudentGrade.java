package com.lwrnsu.student_grade_tracker.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentGrade {

    @NotNull(message = "Student ID is required.")
    @NotBlank(message = "Student ID must not be blank.")
    private String studentID;

    private String subjectCode;
    private String lastName;
    private String firstName;
    private String middleName;

    @Max(100)
    private double grade;

    private String userData;
}
