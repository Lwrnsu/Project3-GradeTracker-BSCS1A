package com.lwrnsu.student_grade_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.lwrnsu.student_grade_tracker.api.ApiResponse;
import com.lwrnsu.student_grade_tracker.models.LogInRequest;
import com.lwrnsu.student_grade_tracker.models.User;
import com.lwrnsu.student_grade_tracker.services.UserServices;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logIn")
    public ApiResponse logIn(@RequestBody LogInRequest logIn) {
        boolean valid = userService.logIn(logIn);
        return new ApiResponse(valid, "Log In Successfully", null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    public ApiResponse signUp(@Valid @RequestBody User user) {
        userService.signUp(user);
        return new ApiResponse(true, "Account Created Successfully", null);
    }
}
