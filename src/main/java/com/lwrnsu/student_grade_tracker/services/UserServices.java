package com.lwrnsu.student_grade_tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lwrnsu.student_grade_tracker.errors.InvalidCredentialsException;
import com.lwrnsu.student_grade_tracker.models.LogInRequest;
import com.lwrnsu.student_grade_tracker.models.Statistics;
import com.lwrnsu.student_grade_tracker.models.Student;
import com.lwrnsu.student_grade_tracker.models.UpdateStudent;
import com.lwrnsu.student_grade_tracker.models.User;
import com.lwrnsu.student_grade_tracker.repository.Database;

@Service
public class UserServices {

    @Autowired
    Database database;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean logIn(LogInRequest logIn) {
        if (database.isUserExist(logIn.getUsername())) {
            if (encoder.matches(logIn.getPassword(), database.getPassword(logIn.getUsername()))) {
                return true;
            }
        }
        throw new InvalidCredentialsException("Invalid Credentials");
    }

    public User setInfo(LogInRequest logIn) {
        String username = logIn.getUsername();
        return new User(username, database.getLastName(username), database.getFirstName(username), database.getMiddleName(username), null);
    }

    public void signUp(User user) {
        String hashedPw = encoder.encode(user.getPassword());
        database.signUp(user.getLastName(), user.getFirstName(), user.getMiddleName(), user.getUsername(), hashedPw);
    }

    public Statistics getStatistics(String username) {
        int id = database.getUserID(username);
        return new Statistics(database.getTotalStudents(id), database.getTotalSubjects(id), database.getTotalPassing(id), database.getTotalFailing(id));
    }

    public void addStudent(Student student) {
        database.addStudent(student);
        int id = database.getStudentDBID(student);
        int year = database.getStudentYearCreation(student);
        String studentId = String.format("%d%d%04d", year, student.getYearLevel(), id);
        student.setStudentId(studentId);
        database.updateStudentID(student);
    }

    public List<Student> getStudent(String username) {
        return database.getStudents(username);
    }

    public void updateStudent(UpdateStudent updateStudent) {
        database.updateStudent(updateStudent);
    }

    public void deleteStudent(String studentID) {
        database.deleteStudent(studentID);
    }
}

