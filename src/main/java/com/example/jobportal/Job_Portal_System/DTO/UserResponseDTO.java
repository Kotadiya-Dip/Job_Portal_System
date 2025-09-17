package com.example.jobportal.Job_Portal_System.DTO;

import lombok.*;

import java.util.List;

//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String userName;
    private String email;
    private List<String> roles;

    public UserResponseDTO(String id, String userName, String email, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }
    public UserResponseDTO(){

    }

    public <E> UserResponseDTO(String userName, String email, List<E> roles) {
        userName=this.userName;
        email=this.email;
        roles= (List<E>) this.roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
