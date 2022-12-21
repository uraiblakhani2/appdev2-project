package com.example.healthpal;

public class User {

    private String email;
    private String fullname;
    private String password;
    private String image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String email, String fullname, String password) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
    }
}
