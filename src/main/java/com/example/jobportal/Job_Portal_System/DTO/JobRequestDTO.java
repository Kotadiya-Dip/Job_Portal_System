package com.example.jobportal.Job_Portal_System.DTO;

import jakarta.validation.constraints.NotBlank;

//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class JobRequestDTO {
    @NotBlank(message = "Job name   is required")
    private String jobTitle;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "companyName is required")
    private String companyName;
//
//    private String location;
//    private String postedByUserId; // for now client sends or will be taken from token later

    public JobRequestDTO(){}

    public JobRequestDTO(String jobTitle, String description, String companyName) {
        this.jobTitle = jobTitle;
        this.description = description;
        this.companyName = companyName;
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
