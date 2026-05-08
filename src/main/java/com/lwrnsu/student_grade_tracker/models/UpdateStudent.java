package com.lwrnsu.student_grade_tracker.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudent {

    private String oldLastName;
    private String oldFirstName;
    private String oldMiddleName;

    @NotNull(message="Last name is required.")
    @NotBlank(message="Last name must not be blank.")
    private String newLastName;

    @NotNull(message="First name is required")
    @NotBlank(message="First name must not be blank.")
    private String newFirstName;

    @NotNull(message="Middle name is required.")
    @NotBlank(message="Middle name must not be blank")
    private String newMiddleName;

    @NotNull(message="User data is required.")
    @NotBlank(message="User data must not be blank")
    private String username;
}
