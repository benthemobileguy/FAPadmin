package com.example.bnotion.admin;

public class Users {
    private String email, username, phone;

    public Users(String email, String username, String phone) {
        this.email = email;
        this.username = username;
        this.phone = phone;
    }
public Users(){

}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
