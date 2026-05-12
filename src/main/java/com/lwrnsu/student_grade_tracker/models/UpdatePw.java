package com.lwrnsu.student_grade_tracker.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePw {

    private String userData;
    private String oldPw;

    @NotNull(message = "Password is required.")
    @Size(min = 8, message = "Password must at least 8 characters.")
    private String newPw;
}
