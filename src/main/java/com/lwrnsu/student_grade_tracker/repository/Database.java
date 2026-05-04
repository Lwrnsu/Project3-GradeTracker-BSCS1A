package com.lwrnsu.student_grade_tracker.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.lwrnsu.student_grade_tracker.errors.DatabaseConnectionException;
import com.lwrnsu.student_grade_tracker.errors.UserAlreadyExistException;
import com.lwrnsu.student_grade_tracker.errors.UserNotFoundException;

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
        String sql = "SELECT password FROM tbl_users WHERE username=?";
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return rs.getString("password");
            }
            throw new UserNotFoundException("User not found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database Connection not found.");
        }
    }

    public void signUp(String lName, String fName, String username, String password) {
        String sql = "INSERT INTO tbl_users(username, password, last_name, first_name) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, lName);
            pstmt.setString(4, fName);
            pstmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UserAlreadyExistException("Username already exist.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database not found.");
        }
    }

    public void signUp(String lName, String fName, String mName, String username, String password) {
        String sql = "INSERT INTO tbl_users(username, password, last_name, first_name, middle_name) VALUES (?, ?, ?, ?, ?)";
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
            throw new UserAlreadyExistException("Username already exist.");
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
            throw new UserNotFoundException("User Not Found.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database Connection not found.");
        }
    }


}
