package com.example.jobportal.Job_Portal_System.Controller;

import com.example.jobportal.Job_Portal_System.DTO.ApplicationRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.ApplicationResponseDTO;
import com.example.jobportal.Job_Portal_System.Service.ApplicationsService;
import com.example.jobportal.Job_Portal_System.entity.Applications;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@Tag(name = "Application APIs")
public class ApplicationController {

    @Autowired
    private ApplicationsService applicationsService;

    // 👇 Only EMPLOYEE can see all applications
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @GetMapping
    @Operation(summary = "get All Application  only ADMIN and EMPLOYEE")
    public List<ApplicationResponseDTO> getAllApplications() {
        return applicationsService.getAllApplications();
    }

    // Apply for a job
    @PostMapping("/apply")
    @PreAuthorize("hasAnyAuthority('APPLICANT','EMPLOYEE')")
    @Operation(summary = "Apply for a job ")
    public ResponseEntity<?> createApplications(
            @Valid @RequestBody ApplicationRequestDTO applicationDTO) {

//        applicationDTO.setUserId(userId);
//        applicationDTO.setJobTitle(jobId);
        ApplicationResponseDTO response = applicationsService.createApplication(applicationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 👇 Applicant or Employee can view by ID
    @PreAuthorize("hasAnyAuthority('APPLICANT','EMPLOYEE','ADMIN')")
    @GetMapping("/id/{appId}")
    @Operation(summary = "get application by AppId ")

    public ApplicationResponseDTO getApplicationById(@PathVariable String appId) {
        return applicationsService.getApplicationById(appId);
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @PutMapping("/id/{appId}")
    @Operation(summary = "update application by AppId only ADMIN and EMPLOYEE")

    public ResponseEntity<?> updateApplicationById(@PathVariable String appId,
                                                   @RequestBody ApplicationRequestDTO applicationDTO){
        try {
            Applications response = applicationsService.updateApplicationById(appId,  applicationDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @DeleteMapping("/id/{appId}")
    @Operation(summary = "delete application by AppId only ADMIN and EMPLOYEE")
    public ResponseEntity<String> deleteApplicationsById(@PathVariable String appId) {
        if (applicationsService.existsById(appId)) {
            applicationsService.deleteApplicationById(appId);
            return ResponseEntity.ok("Application deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }
    }

//    @PreAuthorize("hasAnyAuthority('APPLICANT','EMPLOYEE','ADMIN')")
//    @GetMapping("/user/{userId}")
//    @Operation(summary = "get application by giving user Id")
//    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByUser(@PathVariable String userId) {
//        List<ApplicationResponseDTO> applications = applicationsService.getApplicationByUserId(userId);
//        return ResponseEntity.ok(applications);
//    }
//
//    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
//    @GetMapping("/job/{jobId}")
//    @Operation(summary = "get apppliaction by giving job Id")
//    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByJob(@PathVariable String jobId) {
//        List<ApplicationResponseDTO> applications = applicationsService.getApplicationByJobId(jobId);
//        return ResponseEntity.ok(applications);
//    }

    // pagination for the application
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ApplicationResponseDTO> apps = applicationsService.getAllApplicationsPaginated(page, size);
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/user/paginated/{userId}")
//    @PreAuthorize("hasAnyAuthority('APPLICANT','EMPLOYEE','ADMIN')")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByUserPaginated(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ApplicationResponseDTO> apps = applicationsService.getApplicationsByUserPaginated(userId, page, size);
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/job/paginated/{jobId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByJobPaginated(
            @PathVariable String jobId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ApplicationResponseDTO> apps = applicationsService.getApplicationsByJobPaginated(jobId, page, size);
        return ResponseEntity.ok(apps);
    }

}

