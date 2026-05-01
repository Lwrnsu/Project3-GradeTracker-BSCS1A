package com.lwrnsu.student_grade_tracker.services;

public class UserServices {
    
    public static boolean validateCredentials(String user, String pw) {
        if (user.equals("lwrnsu") && pw.equals("1234")) {
            return true;
        }
        return false;
    }

}
