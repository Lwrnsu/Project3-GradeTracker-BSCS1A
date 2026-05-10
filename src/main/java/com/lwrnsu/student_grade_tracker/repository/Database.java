package com.lwrnsu.student_grade_tracker.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.lwrnsu.student_grade_tracker.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.lwrnsu.student_grade_tracker.errors.DataAlreadyExistException;
import com.lwrnsu.student_grade_tracker.errors.DataNotFoundException;
import com.lwrnsu.student_grade_tracker.errors.DatabaseConnectionException;
import com.lwrnsu.student_grade_tracker.errors.InsertingNullException;

import javax.xml.transform.Result;

@Repository
public class Database {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String name;

    @Value("${spring.datasource.password}")
    private String pw;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, name, pw);
    }

    public boolean isUserExist(String username) {
        String sql = "SELECT username FROM tbl_users WHERE username=?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database Connection not found");
        }
    }

    public String getPassword(String username) {
        String sql = "SELECT password FROM tbl_users WHERE username=?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return rs.getString("password");
            }
            throw new DataNotFoundException("User not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database Connection not found.");
        }
    }

    public void signUp(String lName, String fName, String mName, String username, String password) {
        String sql = "INSERT INTO tbl_users(username, password, last_name, first_name, middle_name) VALUES (?, ?, ?, ?, ?);";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, lName);
            pstmt.setString(4, fName);
            pstmt.setString(5, mName);
            pstmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DataAlreadyExistException("Username already exist.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public String getLastName(String username) {
        String sql = "SELECT last_name FROM tbl_users WHERE username=?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getString("last_name");
            }
            throw new DataNotFoundException("User not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public String getFirstName(String username) {
        String sql = "SELECT first_name FROM tbl_users WHERE username=?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("first_name");
            }
            throw new DataNotFoundException("User not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public String getMiddleName(String username) {
        String sql = "SELECT middle_name FROM tbl_users WHERE username=?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("middle_name");
            }
            throw new DataNotFoundException("User not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public int getUserID(String username) {
        String sql = "SELECT id FROM tbl_users WHERE username=?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
            throw new DataNotFoundException("User Not Found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database Connection not found.");
        }
    }

    public int getTotalStudents(int id) {
        String sql = "SELECT COUNT(*) AS total FROM tbl_students WHERE fk_user_id=?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            throw new DataNotFoundException("User not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }
    
    public int getTotalSubjects(int id) {
        String sql = "SELECT COUNT(*) AS total FROM tbl_subjects WHERE fk_user_id=?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            throw new DataNotFoundException("User not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public int getTotalPassing(int id) {
        String sql = "SELECT COUNT(*) AS total FROM tbl_grades " +
        "JOIN tbl_enrolled ON tbl_enrolled.id = tbl_grades.fk_enrolled_id " +
        "JOIN tbl_students ON tbl_students.id = tbl_enrolled.fk_student_id " +
        "WHERE tbl_students.fk_user_id = ? AND tbl_grades.grade >= 75;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return -1;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public int getTotalFailing(int id) {
        String sql = "SELECT COUNT(*) AS total FROM tbl_grades " +
        "JOIN tbl_enrolled ON tbl_enrolled.id = tbl_grades.fk_enrolled_id " +
        "JOIN tbl_students ON tbl_students.id = tbl_enrolled.fk_student_id " +
        "WHERE tbl_students.fk_user_id = ? AND tbl_grades.grade < 75;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return -1;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO tbl_students(last_name, first_name, middle_name, year_level, fk_user_id) VALUES (?, ?, ?, ?, ?);";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, student.getLastName());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getMiddleName());
            pstmt.setInt(4, student.getYearLevel());
            pstmt.setInt(5, getUserID(student.getUsername()));
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DataAlreadyExistException("Student Already Exist");
        } catch (SQLException e) {
            if (e.getErrorCode() == 0 || e.getErrorCode() == 1049) {
                throw new DatabaseConnectionException("Database not found.");
            }
            if (e.getErrorCode() == 1048) {
                throw new InsertingNullException("Cannot insert a null in a not null column.");
            }
        }
    }

    public int getStudentDBID(Student student) {
        String sql = "SELECT id FROM tbl_students WHERE student_id IS NULL AND last_name=? AND first_name=? AND (middle_name IS NULL OR middle_name = ?) AND year_level = ? AND fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, student.getLastName());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getMiddleName());
            pstmt.setInt(4, student.getYearLevel());
            pstmt.setInt(5, getUserID(student.getUsername()));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
            throw new DataNotFoundException("Error 1");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public int getStudentYearCreation(Student student) {
        String sql = "SELECT YEAR(created_at) AS year FROM tbl_students WHERE fk_user_id=? AND id=?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, getUserID(student.getUsername()));
            pstmt.setInt(2, getStudentDBID(student));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("year");
            }
            throw new DataNotFoundException("Error 2");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void updateStudentID(Student student) {
        String sql = "UPDATE tbl_students SET student_id = ? WHERE id = ?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setInt(2, getStudentDBID(student));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void deleteStudent(String studentID) {
        String sql = "DELETE FROM tbl_students WHERE student_id = ?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, studentID);
            int status = pstmt.executeUpdate();
            if (status == 0) {
                throw new DataNotFoundException("Student not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public List<Student> getStudents(String username) {
        String sql = "SELECT s.student_id, s.last_name, s.first_name, s.middle_name, s.year_level FROM tbl_students s WHERE s.fk_user_id = ? ORDER BY s.last_name, s.first_name, s.middle_name ASC;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, getUserID(username));
            ResultSet rs = pstmt.executeQuery();
            List<Student> list = new ArrayList<>();
            while(rs.next()) {
                Student data = new Student();
                data.setStudentId(rs.getString("student_id"));
                data.setLastName(rs.getString("last_name"));
                data.setFirstName(rs.getString("first_name"));
                data.setMiddleName(rs.getString("middle_name"));
                data.setYearLevel(rs.getInt("year_level"));
                list.add(data);
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void updateStudent(UpdateStudent updateStudent) {
        String sql = "UPDATE tbl_students SET last_name = ?, first_name = ?, middle_name = ? " +
        "WHERE last_name = ? AND first_name = ? AND middle_name = ? AND fk_user_id = ?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, updateStudent.getNewLastName());
            pstmt.setString(2, updateStudent.getNewFirstName());
            pstmt.setString(3, updateStudent.getNewMiddleName());
            pstmt.setString(4, updateStudent.getOldLastName());
            pstmt.setString(5, updateStudent.getOldFirstName());
            pstmt.setString(6, updateStudent.getOldMiddleName());
            pstmt.setInt(7, getUserID(updateStudent.getUsername()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void addSubject(Subject subject) {
        String sql = "INSERT INTO tbl_subjects(subject_code, subject_name, fk_user_id) VALUES (?, ?, ?);";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, subject.getSubjectCode());
            pstmt.setString(2, subject.getSubjectName());
            pstmt.setInt(3, getUserID(subject.getUserData()));
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
          throw new DataAlreadyExistException("Subject code already exists.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public List<Subject> getSubject(String username) {
        String sql = "SELECT subject_code, subject_name FROM tbl_subjects WHERE fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, getUserID(username));
            ResultSet rs = pstmt.executeQuery();
            List<Subject> data = new ArrayList<>();
            while(rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectCode(rs.getString("subject_code"));
                subject.setSubjectName(rs.getString("subject_name"));
                data.add(subject);
            }
            return data;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public String getSubjectName(String subjectCode, String userData) {
        String sql = "SELECT subject_name FROM tbl_subjects WHERE subject_code = ? AND fk_user_id = ?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, subjectCode);
            pstmt.setInt(2, getUserID(userData));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("subject_name");
            }
            throw new DataNotFoundException("Subject not exists.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public SubjectEnrolled getSubjectEnrolled(String userData, String subjectCode) {
        String sql = "SELECT sub.subject_code, sub.subject_name, stud.student_id, stud.last_name, stud.first_name, stud.middle_name, stud.year_level " +
                "FROM tbl_enrolled AS enr " +
                "JOIN tbl_subjects AS sub ON sub.id = enr.fk_subject_id " +
                "JOIN tbl_students AS stud ON stud.id = enr.fk_student_id " +
                "WHERE sub.subject_code = ? AND sub.fk_user_id = ? AND stud.fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            int userID = getUserID(userData);
            pstmt.setString(1, subjectCode);
            pstmt.setInt(2, userID);
            pstmt.setInt(3, userID);
            ResultSet rs = pstmt.executeQuery();
            List<Student> studentEnrolledList = new ArrayList<>();
            while(rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setLastName(rs.getString("last_name"));
                student.setFirstName(rs.getString("first_name"));
                student.setMiddleName(rs.getString("middle_name"));
                student.setYearLevel(rs.getInt("year_level"));
                studentEnrolledList.add(student);
            }
            return new SubjectEnrolled(subjectCode, getSubjectName(subjectCode, userData), studentEnrolledList);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public int getStudentDBID(String studentID, String userData) {
        String sql = "SELECT id FROM tbl_students WHERE student_id = ? AND fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, studentID);
            pstmt.setInt(2, getUserID(userData));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new DataNotFoundException("Student not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public int getSubjectID(String subjectCode, String userData) {
        String sql = "SELECT id FROM tbl_subjects WHERE subject_code = ? AND fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, subjectCode);
            pstmt.setInt(2, getUserID(userData));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
            throw new DataNotFoundException("Subject not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void enrollStudent(EnrollStudent enrollStudent) {
        String sql = "INSERT INTO tbl_enrolled(fk_student_id, fk_subject_id) VALUES (?,?);";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, getStudentDBID(enrollStudent.getStudentID(), enrollStudent.getUserData()));
            pstmt.setInt(2, getSubjectID(enrollStudent.getSubjectCode(), enrollStudent.getUserData()));
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DataAlreadyExistException("Student already exists.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void deleteEnrolledStudent(String studentID, String subjectCode, String userData) {
        String sql = "DELETE enr FROM tbl_enrolled AS enr " +
                "WHERE enr.fk_student_id = ? AND enr.fk_subject_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, getStudentDBID(studentID, userData));
            pstmt.setInt(2, getSubjectID(subjectCode, userData));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void updateSubject(UpdateSubject updateSubject) {
        String sql = "UPDATE tbl_subjects SET subject_code = ?, subject_name = ? " +
                "WHERE subject_code = ? AND subject_name = ? AND fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, updateSubject.getNewSubjectCode());
            pstmt.setString(2, updateSubject.getNewSubjectName());
            pstmt.setString(3, updateSubject.getOldSubjectCode());
            pstmt.setString(4, updateSubject.getOldSubjectName());
            pstmt.setInt(5, getUserID(updateSubject.getUserData()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void deleteSubject(String userData, String subjectCode) {
        String sql = "DELETE FROM tbl_subjects WHERE subject_code = ? AND fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, subjectCode);
            pstmt.setInt(2, getUserID(userData));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public int getEnrolledID(String studentID, String userData, String subjectCode) {
        String sql = "SELECT enr.id FROM tbl_enrolled AS enr " +
                "JOIN tbl_students AS stud ON stud.id = enr.fk_student_id " +
                "JOIN tbl_subjects AS sub ON sub.id = enr.fk_subject_id " +
                "WHERE stud.student_id = ? AND sub.subject_code = ? " +
                "AND stud.fk_user_id = ? AND sub.fk_user_id = ?;";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            int id = getUserID(userData);
            pstmt.setString(1, studentID);
            pstmt.setString(2, subjectCode);
            pstmt.setInt(3, id);
            pstmt.setInt(4, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new DataNotFoundException("Enrolled Student not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void addStudentGradeList(EnrollStudent enrollStudent) {
        String sql = "INSERT INTO tbl_grades(fk_enrolled_id) VALUES (?);";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, getEnrolledID(enrollStudent.getStudentID(), enrollStudent.getUserData(), enrollStudent.getSubjectCode()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public SubjectGrade getStudentGradeList(String userData, String subjectCode) {
        String sql = "SELECT stud.student_id, stud.last_name, stud.first_name, stud.middle_name, grd.grade FROM tbl_grades AS grd " +
                "JOIN tbl_enrolled AS enr ON enr.id = grd.fk_enrolled_id " +
                "JOIN tbl_students AS stud ON stud.id = enr.fk_student_id " +
                "JOIN tbl_subjects AS sub ON sub.id = enr.fk_subject_id " +
                "WHERE stud.fk_user_id = ? AND sub.subject_code = ? AND sub.fk_user_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            int userID = getUserID(userData);
            pstmt.setInt(1, userID);
            pstmt.setString(2, subjectCode);
            pstmt.setInt(3, userID);
            ResultSet rs = pstmt.executeQuery();
            List<StudentGrade> data = new ArrayList<>();
            while(rs.next()) {
                StudentGrade student = new StudentGrade();
                student.setStudentID(rs.getString("student_id"));
                student.setLastName(rs.getString("last_name"));
                student.setFirstName(rs.getString("first_name"));
                student.setMiddleName(rs.getString("middle_name"));
                student.setGrade(rs.getDouble("grade"));
                data.add(student);
            }
            return new SubjectGrade(userData, subjectCode, getSubjectName(subjectCode, userData), data);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void deleteStudentGradeList(String studentID, String userData, String subjectCode) {
        String sql = "DELETE FROM tbl_grades WHERE fk_enrolled_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, getEnrolledID(studentID, userData, subjectCode));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void updateStudentGrade(List<StudentGrade> array) {
        String sql = "UPDATE tbl_grades SET grade = ? WHERE fk_enrolled_id = ?;";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            array.forEach(e -> {
               try {
                   pstmt.setBigDecimal(1, new BigDecimal(e.getGrade()));
                   pstmt.setInt(2, getEnrolledID(e.getStudentID(), e.getUserData(), e.getSubjectCode()));
                   pstmt.executeUpdate();
               } catch (SQLException err) {
                   err.printStackTrace();
                   throw new DataNotFoundException("Student not found.");
               }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }
}