package com.example.jobportal.Job_Portal_System.DTO;

import com.example.jobportal.Job_Portal_System.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;

public class ApplicationRequestDTO {
    @NotBlank(message = "userName is required")
    private String userName;

    @NotBlank(message = "job name  is required")
    private String jobTitle;

    @NotBlank(message = "Resume is Required")
    private String resume;

    // ✅ use enum instead of String
    private ApplicationStatus status;

    public ApplicationRequestDTO() {}

    public ApplicationRequestDTO(String userName, String jobTitle, String resume, ApplicationStatus status) {
        this.userName = userName;
        this.jobTitle = jobTitle;
        this.resume = resume;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
