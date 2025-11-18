package com.example.jobportal.Job_Portal_System.entity;

import com.example.jobportal.Job_Portal_System.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("applications")
//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class Applications {

    @Id
    private String id;

    @NotBlank(message = "User ID is required")
    private String userId;// just store userId for now

    @NotBlank(message = "Job ID is required")
    private String jobId;    // just store jobId for now
    @NotBlank(message = "Resume is required")
    private String resume;
    private ApplicationStatus status = ApplicationStatus.PENDING;

   public Applications(){}

    public Applications(String id, String userId, String jobId,  ApplicationStatus status,String resume) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.status = status;
        this.resume = resume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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
