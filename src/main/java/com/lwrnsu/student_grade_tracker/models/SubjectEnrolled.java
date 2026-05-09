package com.lwrnsu.student_grade_tracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEnrolled {
    private String subjectCode;
    private String subjectName;
    private List<Student> studentEnrolledList;
}
