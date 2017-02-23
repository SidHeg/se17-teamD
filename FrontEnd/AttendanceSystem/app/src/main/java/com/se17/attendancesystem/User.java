package com.se17.attendancesystem;

/**
 * Created by saravanan on 2/16/17.
 */

public class User {

    private String userId;
    private String password;
    private boolean isProfessor;
    private boolean isStudent;

    public User(){
        isProfessor = false;
        isStudent = false;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
