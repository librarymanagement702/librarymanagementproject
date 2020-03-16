package com.vjay.libararymanagement.model;

public class AppUser {
    private String name;
    private String userName;
    private String registerNumber;
    private String password;
    private UserRole role;

    public AppUser(){

    }

    public AppUser(String name, String userName, String registerNumber, String password, UserRole role) {
        this.name = name;
        this.userName = userName;
        this.registerNumber = registerNumber;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
