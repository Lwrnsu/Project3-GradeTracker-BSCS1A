package com.lwrnsu.student_grade_tracker.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {

    private String oldUserID;

    @NotNull(message = "Username is required.")
    @NotBlank(message = "Username must not be blank.")
    private String userID;

    @NotNull(message = "Last name is required.")
    @NotBlank(message = "Last name must not be blank.")
    private String lastName;

    @NotNull(message = "First name is required.")
    @NotBlank(message = "First name must not be blank.")
    private String firstName;

    private String middleName;

}
