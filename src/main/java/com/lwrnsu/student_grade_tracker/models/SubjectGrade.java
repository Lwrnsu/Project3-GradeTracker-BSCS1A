package com.lwrnsu.student_grade_tracker.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectGrade {

    private String userData;
    private String subjectCode;
    private String subjectName;
    private List<StudentGrade> studentGrade;

}
