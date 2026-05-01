package com.lwrnsu.student_grade_tracker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lwrnsu.student_grade_tracker.services.UserServices;
import com.lwrnsu.student_grade_tracker.dto.AuthResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/auth")
    public AuthResponse auth(@RequestParam String user,
        @RequestParam String pw
    ) {
        return new AuthResponse(UserServices.validateCredentials(user, pw));
    }

}
