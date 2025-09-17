package com.example.jobportal.Job_Portal_System.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
//@Data
//@Getter
//@Setter
public class User  {
    @Id
    private String id;
    @NotBlank(message = "Username is required")
    private String userName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "At least one role is required")
    private List<String> roles = new ArrayList<>();

    // for updation
    private String oldEmail;         // temporarily store old email
    private boolean requiresReLogin; // default false

    public User() {}

    public User(String id, String userName, String email, String password, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    //    public User(String number, String mail, String number1, List<String> user) {
//
//    }


    //
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public boolean isRequiresReLogin() {
        return requiresReLogin;
    }

    public void setRequiresReLogin(boolean requiresReLogin) {
        this.requiresReLogin = requiresReLogin;
    }
}
