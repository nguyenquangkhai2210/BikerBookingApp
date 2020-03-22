package com.example.bikebookingapp.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String fullname, email, password;

    public UserModel() {

    }

    public UserModel( String fullname, String email, String password) {

        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }





    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
