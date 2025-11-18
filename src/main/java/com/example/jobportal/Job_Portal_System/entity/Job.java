package com.example.jobportal.Job_Portal_System.entity;


import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("jobs")
//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class Job {

    @Id
    private String id;

    @NotBlank(message = "jobTitle is required")
    private String jobTitle;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Company name is required")
    private String companyName;

//    @NotBlank(message = "location is required")
//    private String location;

    public Job(){}

    public Job(String id, String jobTitle, String description, String companyName) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.description = description;
        this.companyName = companyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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
