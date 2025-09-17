package com.example.jobportal.Job_Portal_System.Controller;

import com.example.jobportal.Job_Portal_System.DTO.JobRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.JobResponseDTO;
import com.example.jobportal.Job_Portal_System.Service.JobService;
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
@RequestMapping("/jobs")
@Tag(name = "Job APIs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Get all jobs
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllJob() {
        List<JobResponseDTO> jobs = jobService.getAllJob();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    // Create job
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @PostMapping
    @Operation(summary = "create a job only ADMIN and EMPLOYEE")
    public ResponseEntity<?> createJob(@Valid @RequestBody JobRequestDTO jobDto) {
        try {
            JobResponseDTO savedJob = jobService.createJob(jobDto);
            return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get job by ID
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @GetMapping("id/{jobId}")
    @Operation(summary = "get job by jobId only ADMIN and EMPLOYEE")
    public ResponseEntity<?> getJobById(@PathVariable String jobId) {
        try {
            JobResponseDTO job = jobService.getJobById(jobId);
            return new ResponseEntity<>(job, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update job by ID
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @PutMapping("id/{jobId}")
    @Operation(summary = "update job by jobId only ADMIN and EMPLOYEE")

    public ResponseEntity<?> updateJobById(@PathVariable String jobId, @RequestBody JobRequestDTO jobDto) {
        try {
            JobResponseDTO updatedJob = jobService.updateJob(jobId, jobDto);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete job by ID
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @DeleteMapping("id/{jobId}")
    @Operation(summary = "delete job by jobId only ADMIN and EMPLOYEE")

    public ResponseEntity<String> deleteJobById(@PathVariable String jobId) {
        try {
            jobService.deleteJobById(jobId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    // Search job by title and company
//    @GetMapping("/search/title/{jobTitle}")
//    @Operation(summary = "search job by Name and companyName")
//    public ResponseEntity<?> searchJobsByTitleAndCompanyName(
//            @PathVariable String jobTitle,
//            @RequestParam(required = false) String companyName) {
//        return ResponseEntity.ok(jobService.searchByJobTitleAndCompanyName(jobTitle, companyName));
//    }

    @GetMapping("/paginated")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    public ResponseEntity<List<JobResponseDTO>> getJobsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<JobResponseDTO> jobs = jobService.getAllJobPaginated(page, size);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/search/paginated/title/{jobTitle}")
    public ResponseEntity<List<JobResponseDTO>> searchJobsPaginated(
            @PathVariable String jobTitle,
            @RequestParam(required = false) String companyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<JobResponseDTO> jobs = jobService.searchJobsByTitleAndCompanyPaginated(jobTitle, companyName, page, size);
        return ResponseEntity.ok(jobs);
    }


}
