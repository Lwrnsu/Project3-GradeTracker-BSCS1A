package com.lwrnsu.student_grade_tracker.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubject {
    private String oldSubjectCode;
    private String oldSubjectName;
    private String userData;

    @NotNull(message = "Subject code is required.")
    @NotBlank(message = "Subject code must not be blank.")
    private String newSubjectCode;

    @NotNull(message = "Subject code is required.")
    @NotBlank(message = "Subject code must not be blank.")
    private String newSubjectName;
}
