package com.lwrnsu.student_grade_tracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PathController {

    @GetMapping("/")
    public String auth() {
        return "index";
    }

    @GetMapping("/home")
    public String dashboard() {
        return "home";
    }

}
