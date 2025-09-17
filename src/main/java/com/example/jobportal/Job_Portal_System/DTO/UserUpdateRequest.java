package com.example.jobportal.Job_Portal_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//@Data
public class UserUpdateRequest {

    private String userName;      // optional
    private String email;         // optional
//    private String phone;         // optional
//    @NotBlank(message = "old password is required to change the password")
    private String password;   // required only if changing password
//    @NotBlank(message = "Old password required to change the password")
    private String newPassword;// required only if changing password


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
