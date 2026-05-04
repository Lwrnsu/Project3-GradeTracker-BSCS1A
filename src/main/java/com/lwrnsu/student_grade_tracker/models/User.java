package com.lwrnsu.student_grade_tracker.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @NotNull(message="Username is required.")
    @NotBlank(message="Username cannot be blank.")
    private String username;

    @NotNull(message="Last Name is required.")
    @NotBlank(message="Last Name cannot be blank")
    private String lastName;

    @NotNull(message="First Name is required.")
    @NotBlank(message="First Name cannot be blank")
    private String firstName;

    private String middleName;

    @NotNull(message="Password is required.")
    @Size(min=8, message="Password must contain at least 8 characters.")
    private String password;

}
