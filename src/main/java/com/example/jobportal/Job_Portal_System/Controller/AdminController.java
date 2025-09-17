package com.example.jobportal.Job_Portal_System.Controller;

import com.example.jobportal.Job_Portal_System.DTO.AdminDashboardDTO;
import com.example.jobportal.Job_Portal_System.DTO.ApplicationRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.JobRequestDTO;
import com.example.jobportal.Job_Portal_System.Service.ApplicationsService;
import com.example.jobportal.Job_Portal_System.Service.JobService;
import com.example.jobportal.Job_Portal_System.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicationsService applicationsService;

    // ✅ Manage Users
    @GetMapping("/users")
    @Operation(summary = "get All user")

    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @DeleteMapping("/user/id/{userId}")
    @Operation(summary = "delete user by id")

    public ResponseEntity<?> deleteUSerById(@PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>( "user deleted successfully",HttpStatus.OK);
    }

    // ✅ Manage Jobs
    @GetMapping("/jobs")
    @Operation(summary = "get All jobs")
    public ResponseEntity<?> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJob());
    }

    @PutMapping("/jobs/id/{jobId}")
    @Operation(summary = "update job by id")
    public ResponseEntity<?> updateJobByID(@PathVariable String jobId, @RequestBody JobRequestDTO jobRequestDTO){
        jobService.updateJob(jobId,jobRequestDTO);
        return new ResponseEntity<>("job updated successfully",HttpStatus.OK);
    }
    @DeleteMapping("/jobs/id/{id}")
    @Operation(summary = "delete job by id")
    public ResponseEntity<?> deleteJob(@PathVariable String id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    // ✅ Manage Applications
    @GetMapping("/applications")
    @Operation(summary = "get All Application")
    public ResponseEntity<?> getAllApplications() {
        return ResponseEntity.ok(applicationsService.getAllApplications());
    }
    @PutMapping("/applications/id/{id}")
    @Operation(summary = "update Application by Id")
    public ResponseEntity<?> updateApplicationById(@PathVariable String id, @RequestBody ApplicationRequestDTO dto){
        applicationsService.updateApplicationById(id,dto);
        return new ResponseEntity<>("Application updated Successfully",HttpStatus.OK);
    }
    @DeleteMapping("/applications/id/{id}")
    @Operation(summary = "delete Application by Id")
    public ResponseEntity<?> deleteApplicationById(@PathVariable String id) {
        applicationsService.deleteApplicationById(id);
        return ResponseEntity.ok("Application deleted successfully");
    }
    @GetMapping("/dashboard")
    @Operation(summary = "get the count of all things")

    public ResponseEntity<AdminDashboardDTO> getDashboard() {
        long totalUsers = userService.countUsers();
        long totalApplicants = userService.countByRole("APPLICANT");
        long totalEmployees = userService.countByRole("EMPLOYEE");
        long totalAdmins = userService.countByRole("ADMIN");

        long totalJobs = jobService.countJobs();
        long totalApplications = applicationsService.countApplications();

        AdminDashboardDTO dashboard = new AdminDashboardDTO(
                totalUsers,
                totalApplicants,
                totalEmployees,
                totalAdmins,
                totalJobs,
                totalApplications
        );

        return ResponseEntity.ok(dashboard);
    }

}
