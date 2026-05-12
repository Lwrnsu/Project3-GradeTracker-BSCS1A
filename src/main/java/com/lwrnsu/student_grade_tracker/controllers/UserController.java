package com.lwrnsu.student_grade_tracker.controllers;

import com.lwrnsu.student_grade_tracker.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.lwrnsu.student_grade_tracker.api.ApiResponse;
import com.lwrnsu.student_grade_tracker.services.UserServices;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logIn")
    public ApiResponse logIn(@RequestBody LogInRequest logIn) {
        boolean valid = userService.logIn(logIn);
        User info = userService.setInfo(logIn);
        return new ApiResponse(valid, "Log In Successfully", info);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    public ApiResponse signUp(@Valid @RequestBody User user) {
        userService.signUp(user);
        return new ApiResponse(true, "Account Created Successfully", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stats")
    public ApiResponse getStatistics(@RequestParam String username) {
        return new ApiResponse(true, "Data retrieved successfully!", userService.getStatistics(username));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add/student")
    public ApiResponse addStudent(@Valid @RequestBody Student student) {
        userService.addStudent(student);
        return new ApiResponse(true, "Student added successfully.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/student")
    public ApiResponse getStudent(@RequestParam String username) {
        return new ApiResponse(true, "Student/s successfully retrieved.", userService.getStudent(username));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/student")
    public ApiResponse updateStudent(@Valid @RequestBody UpdateStudent updateStudent) {
        userService.updateStudent(updateStudent);
        return new ApiResponse(true, "Student successfully updated.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/student")
    public ApiResponse deleteStudent(@RequestParam String studentID) {
        userService.deleteStudent(studentID);
        return new ApiResponse(true, "Student deleted successfully.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add/subject")
    public ApiResponse addSubject(@Valid @RequestBody Subject subject) {
        userService.addSubject(subject);
        return new ApiResponse(true, "Subject added successfully.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/subject")
    public ApiResponse getSubject(@RequestParam String username) {
        return new ApiResponse(true, "Subject/s successfully retrieved.", userService.getSubject(username));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/subject/{userData}/{subjectCode}")
    public ApiResponse getSubjectEnrolled(@PathVariable String userData, @PathVariable String subjectCode) {
        return new ApiResponse(true, "Subject data successfully retrieved.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/subject/name/{userData}/{subjectCode}")
    public ApiResponse getSubjectName(@PathVariable String userData, @PathVariable String subjectCode) {
        return new ApiResponse(true, "Subject name successfully retrieved.", userService.getSubjectName(userData, subjectCode));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add/subject/student")
    public ApiResponse enrollStudent(@Valid @RequestBody EnrollStudent enrollStudent) {
        userService.enrollStudent(enrollStudent);
        userService.addStudentGradeList(enrollStudent);
        return new ApiResponse(true, "Student successfully enrolled.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/subject/student")
    public ApiResponse deleteEnrolledStudent(@RequestParam String studentID, @RequestParam String subjectCode, @RequestParam String userData) {
        userService.deleteStudentGradeList(studentID, userData, subjectCode);
        userService.deleteEnrolledStudent(studentID, subjectCode, userData);
        return new ApiResponse(true, "Student successfully deleted from the subject", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/subject")
    public ApiResponse updateSubject(@Valid @RequestBody UpdateSubject updateSubject) {
        userService.updateSubject(updateSubject);
        return new ApiResponse(true, "Subject updated successfully", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/subject")
    public ApiResponse deleteSubject(@RequestParam String userData, @RequestParam String subjectCode) {
        userService.deleteSubject(userData, subjectCode);
        return new ApiResponse(true, "Subject Deleted Successfully", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/grades/{userData}/{subjectCode}")
    public ApiResponse getStudentGrade(@PathVariable String userData, @PathVariable String subjectCode) {
        return new ApiResponse(true, "Student grade successfully retrieved.", userService.getStudentGradeList(userData, subjectCode));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/grades/student")
    public ApiResponse updateStudentGrade(@RequestBody List<StudentGrade> array) {
        userService.updateStudentGrade(array);
        return new ApiResponse(true, "Grades updated successfully.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public ApiResponse updateUser(@RequestBody UpdateUser updateUser) {
        userService.updateUser(updateUser);
        return new ApiResponse(true, "User successfully updated.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/isEnrolled")
    public ApiResponse isStudentEnrolled(@RequestParam String userData, @RequestParam String studentID, @RequestParam String subjectCode) {
        return new ApiResponse(userService.isStudentEnrolled(userData, studentID, subjectCode), "Successfully checked the status.", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/password")
    public ApiResponse changePw(@Valid @RequestBody UpdatePw updatePw) {
        userService.changePw(updatePw);
        return new ApiResponse(true, "Password changed successfully.", null);
    }
}
