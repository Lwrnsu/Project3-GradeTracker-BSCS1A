package com.lwrnsu.student_grade_tracker.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String studentId;

    @NotNull(message="Last name is required.")
    @NotBlank(message="Last name cannot be blank.")
    private String lastName;

    @NotNull(message="First name is required.")
    @NotBlank(message="First name cannot be blank.")
    private String firstName;

    private String middleName;

    @NotNull(message="Year level is required.")
    @Min(value=1, message="Must be at least 1st Year.")
    @Max(value=4, message="Must no exceed 4th Year.")
    private int yearLevel;

    private String username;
}
