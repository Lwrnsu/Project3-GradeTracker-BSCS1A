package com.lwrnsu.student_grade_tracker.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.lwrnsu.student_grade_tracker.errors.DataAlreadyExistException;
import com.lwrnsu.student_grade_tracker.errors.DataNotFoundException;
import com.lwrnsu.student_grade_tracker.errors.DatabaseConnectionException;
import com.lwrnsu.student_grade_tracker.errors.InsertingNullException;
import com.lwrnsu.student_grade_tracker.models.Student;

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
            throw new DataNotFoundException("User not found.");
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
            throw new DataNotFoundException("User not found.");
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void updateStudentID(Student student) {
        String sql = "UPDATE tbl_students SET student_id = ?;";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, student.getStudentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Database not found.");
        }
    }

}
