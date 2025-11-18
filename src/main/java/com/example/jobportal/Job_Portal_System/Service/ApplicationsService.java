package com.example.jobportal.Job_Portal_System.Service;

import com.example.jobportal.Job_Portal_System.DTO.ApplicationRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.ApplicationResponseDTO;
import com.example.jobportal.Job_Portal_System.DTO.UserResponseDTO;
import com.example.jobportal.Job_Portal_System.ExceptionHandler.ApplicationNotFoundException;
import com.example.jobportal.Job_Portal_System.ExceptionHandler.JobNotFoundException;
import com.example.jobportal.Job_Portal_System.ExceptionHandler.UserNotFoundException;
import com.example.jobportal.Job_Portal_System.Repository.ApplicationsRepository;
import com.example.jobportal.Job_Portal_System.Repository.JobRepository;
import com.example.jobportal.Job_Portal_System.Repository.UserRepository;
import com.example.jobportal.Job_Portal_System.entity.Applications;
import com.example.jobportal.Job_Portal_System.enums.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.jobportal.Job_Portal_System.entity.User;
import com.example.jobportal.Job_Portal_System.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmailService emailService;

    // Create application
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO request) {
        // 1. Find User by userName
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + request.getUserName()));

        // 2. Find Job by jobTitle
        Job job = jobRepository.findByJobTitle(request.getJobTitle())
                .orElseThrow(() -> new JobNotFoundException("Job not found with jobTitle: " + request.getJobTitle()));

        // 3. Check if user already applied
        if (applicationsRepository.findByUserIdAndJobId(user.getId(), job.getId()).isPresent()) {
            throw new RuntimeException("User has already applied for this job");
        }

        // 4. Create new Application
        Applications app = new Applications();
        app.setUserId(user.getId());   // use ID internally
        app.setJobId(job.getId());     // use ID internally

        // Default status = PENDING
        if (request.getStatus() == null) {
            app.setStatus(ApplicationStatus.PENDING);
        } else {
            app.setStatus(request.getStatus());
        }

        app.setResume(request.getResume());

        Applications savedApp = applicationsRepository.save(app);

        // 5. Return response with full details
        return mapToResponseDTO(savedApp);
    }


    // Get all applications
    public List<ApplicationResponseDTO> getAllApplications() {
        List<Applications> apps = applicationsRepository.findAll();
        List<ApplicationResponseDTO> dtoList = new ArrayList<>();
        for (Applications app : apps) {
            dtoList.add(mapToResponseDTO(app));
        }
        return dtoList;
    }

    // Get by ID
    public ApplicationResponseDTO getApplicationById(String id) {
        Applications app = applicationsRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));
        return mapToResponseDTO(app);
    }

    // Delete by ID
    public void deleteApplicationById(String id) {
        applicationsRepository.deleteById(id);
    }

    // Check if application exists
    public boolean existsById(String id) {
        return applicationsRepository.existsById(id);
    }



    public Applications updateApplicationById(String id, ApplicationRequestDTO updatedApplication) {
        Applications app = applicationsRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));

        if (updatedApplication.getStatus() != null) {
            ApplicationStatus newStatus = updatedApplication.getStatus();

            if (!app.getStatus().equals(newStatus)) {
                app.setStatus(newStatus);

                // Fetch candidate and job
                User candidate = userRepository.findById(app.getUserId())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
                Job job = jobRepository.findById(app.getJobId())
                        .orElseThrow(() -> new JobNotFoundException("Job not found"));

                String subject;
                String templateName;

                if (newStatus == ApplicationStatus.ACCEPTED) {
                    subject = "🎉 Application Accepted - " + job.getJobTitle();
                    templateName = "application-accepted"; // resources/templates/emails/application-accepted.html
                } else if (newStatus == ApplicationStatus.REJECTED) {
                    subject = "❌ Application Rejected - " + job.getJobTitle();
                    templateName = "application-rejected"; // resources/templates/emails/application-rejected.html
                } else {
                    subject = null;
                    templateName = null;
                }

                if (subject != null && templateName != null) {
                    Map<String, Object> variables = Map.of(
                            "userName", candidate.getUserName(),
                            "jobTitle", job.getJobTitle(),
                            "companyName", job.getCompanyName()
                    );

                    emailService.sendMailBasedOnStatus(
                            candidate.getEmail(),
                            subject,
                            templateName,
                            variables
                    );

                }

                // Promote role if accepted
                if (newStatus == ApplicationStatus.ACCEPTED) {
                    if (candidate.getRoles() == null) {
                        candidate.setRoles(new ArrayList<>());
                    }
                    if (!candidate.getRoles().contains("EMPLOYEE")) {
                        candidate.getRoles().add("EMPLOYEE");
                    }
                    userRepository.save(candidate);
                }
            }
        }

        if (updatedApplication.getResume() != null) {
            app.setResume(updatedApplication.getResume());
        }

        return applicationsRepository.save(app);
    }



//    // Get applications by User ID
//    public List<ApplicationResponseDTO> getApplicationByUserId(String userId) {
//        List<Applications> apps = applicationsRepository.findByUserId(userId);
//        List<ApplicationResponseDTO> dtoList = new ArrayList<>();
//        for (Applications app : apps) {
//            dtoList.add(mapToResponseDTO(app));
//        }
//        return dtoList;
//    }

//    // Get applications by Job ID
//    public List<ApplicationResponseDTO> getApplicationByJobId(String jobId) {
//        List<Applications> apps = applicationsRepository.findByJobId(jobId);
//        List<ApplicationResponseDTO> dtoList = new ArrayList<>();
//        for (Applications app : apps) {
//            dtoList.add(mapToResponseDTO(app));
//        }
//        return dtoList;
//    }

//    // Get by User ID & Job ID
//    public ApplicationResponseDTO getByUserIdAndJobId(String userId, String jobId) {
//        Applications app = applicationsRepository.findByUserIdAndJobId(userId, jobId)
//                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));
//        return mapToResponseDTO(app);
//    }

    // ✅ Mapper method: always attaches User + Job details
    // ✅ Mapper method: always attaches User + Job details safely
    private ApplicationResponseDTO mapToResponseDTO(Applications app) {
        ApplicationResponseDTO dto = new ApplicationResponseDTO();
        dto.setId(app.getId());
        dto.setStatus(app.getStatus());
        dto.setResume(app.getResume());

        // Attach User (safe)
        User user = userRepository.findById(app.getUserId()).orElse(null);
        if (user != null) {
            dto.setApplicant(mapToUserResponse(user)); // ✅ no password
        }

        // Attach Job
        Job job = jobRepository.findById(app.getJobId()).orElse(null);
        dto.setJob(job);

        return dto;
    }
    public long countApplications() {
        return applicationsRepository.count();
    }

    // New method: get paginated applications
    public List<ApplicationResponseDTO> getAllApplicationsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Applications> appsPage = applicationsRepository.findAll(pageable);
        return appsPage.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Applications by User with pagination
    public List<ApplicationResponseDTO> getApplicationsByUserPaginated(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Applications> appsPage = applicationsRepository.findByUserId(userId, pageable);
        return appsPage.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Applications by Job with pagination
    public List<ApplicationResponseDTO> getApplicationsByJobPaginated(String jobId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Applications> appsPage = applicationsRepository.findByJobId(jobId, pageable);
        return appsPage.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }



    // ✅ Convert entity User → safe UserResponseDTO
    private UserResponseDTO mapToUserResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRoles()
        );
    }

}
