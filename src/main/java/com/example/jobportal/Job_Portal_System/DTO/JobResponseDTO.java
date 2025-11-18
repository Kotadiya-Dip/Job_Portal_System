package com.example.jobportal.Job_Portal_System.DTO;

import lombok.*;

//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class JobResponseDTO {
    private String id;
    private String title;
    private String description;
    private String companyName;
//    private String location;
//    private String postedByUserId;

    public JobResponseDTO(){}

    public JobResponseDTO(String id, String title, String description, String companyName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.companyName = companyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
