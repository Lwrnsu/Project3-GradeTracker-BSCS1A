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

    @GetMapping("/student")
    public String student() {
        return "student";
    }

    @GetMapping("/subject")
    public String subject() {
        return "subject";
    }

    @GetMapping("/grades")
    public String grades() {
        return "grades";
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }

    @GetMapping("/subject/{userData}/{subjectCode}")
    public String subjectEnrolled() {
        return "subjectEnrolled";
    }

    @GetMapping("/grades/{userData}/{subjectCode}")
    public String subjectGrades() {
        return "subjectGrades";
    }
}
