package com.lwrnsu.student_grade_tracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @NotNull(message = "Subject code is required.")
    @NotBlank(message = "Subject code must not be blank.")
    private String subjectCode;

    @NotNull(message = "Subject name is required.")
    @NotBlank(message = "Subject name must not be blank.")
    private String subjectName;

    @NotNull(message = "User data not found.")
    @NotBlank(message = "What the hell bro???")
    private String userData;
}
