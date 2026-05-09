package com.lwrnsu.student_grade_tracker.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollStudent {

    @NotNull(message = "Student ID is required")
    @NotBlank(message = "Student ID must not be blank.")
    private String studentID;

    @NotNull(message = "Subject Code is required")
    @NotBlank(message = "Subject Code must not be blank.")
    private String subjectCode;

    @NotNull(message = "User Data is required")
    @NotBlank(message = "User Data must not be blank.")
    private String userData;
}
