package com.example.jobportal.Job_Portal_System.DTO;

import com.example.jobportal.Job_Portal_System.entity.User;
import com.example.jobportal.Job_Portal_System.entity.Job;
import com.example.jobportal.Job_Portal_System.enums.ApplicationStatus;

public class ApplicationResponseDTO {

    private String id;
    // ✅ enum instead of String
    private ApplicationStatus status;
    private String resume;
    private UserResponseDTO applicant;
    private Job job;

    public ApplicationResponseDTO() {}

    public ApplicationResponseDTO(String id, ApplicationStatus status, String resume, UserResponseDTO applicant, Job job) {
        this.id = id;
        this.status = status;
        this.resume = resume;
        this.applicant = applicant;
        this.job = job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public UserResponseDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(UserResponseDTO applicant) {
        this.applicant = applicant;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
