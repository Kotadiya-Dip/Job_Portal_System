package com.example.jobportal.Job_Portal_System.ServiceTests;

import com.example.jobportal.Job_Portal_System.DTO.ApplicationRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.ApplicationResponseDTO;
import com.example.jobportal.Job_Portal_System.Repository.ApplicationsRepository;
import com.example.jobportal.Job_Portal_System.Repository.JobRepository;
import com.example.jobportal.Job_Portal_System.Repository.UserRepository;
import com.example.jobportal.Job_Portal_System.Service.ApplicationsService;
import com.example.jobportal.Job_Portal_System.entity.Applications;
import com.example.jobportal.Job_Portal_System.entity.Job;
import com.example.jobportal.Job_Portal_System.entity.User;
import com.example.jobportal.Job_Portal_System.enums.ApplicationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationsServiceTest {

    @Mock private ApplicationsRepository applicationsRepository;
    @Mock private UserRepository userRepository;
    @Mock private JobRepository jobRepository;

    @InjectMocks private ApplicationsService applicationsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createApplication_Success() {
        ApplicationRequestDTO req = new ApplicationRequestDTO("testUser", "Java Dev", "resume.pdf", null);

        User user = new User("1", "testUser", "test@gmail.com", "pass", List.of("APPLICANT"));
        Job job = new Job("1", "Java Dev", "Backend", "ABC");
        Applications entity = new Applications("1", user.getId(), job.getId(), ApplicationStatus.PENDING, "resume.pdf");

        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(user));
        when(jobRepository.findByJobTitle("Java Dev")).thenReturn(Optional.of(job));
        when(applicationsRepository.findByUserIdAndJobId(user.getId(), job.getId())).thenReturn(Optional.empty());
        when(applicationsRepository.save(any(Applications.class))).thenReturn(entity);

        ApplicationResponseDTO response = applicationsService.createApplication(req);

        assertEquals("PENDING", response.getStatus().name());
        assertEquals("resume.pdf", response.getResume());
    }

    @Test
    void createApplication_AlreadyApplied() {
        ApplicationRequestDTO req = new ApplicationRequestDTO("testUser", "Java Dev", "resume.pdf", null);

        User user = new User("1", "testUser", "test@gmail.com", "pass", List.of("APPLICANT"));
        Job job = new Job("1", "Java Dev", "Backend", "ABC");
        Applications entity = new Applications("1", user.getId(), job.getId(), ApplicationStatus.PENDING, "resume.pdf");

        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(user));
        when(jobRepository.findByJobTitle("Java Dev")).thenReturn(Optional.of(job));
        when(applicationsRepository.findByUserIdAndJobId(user.getId(), job.getId())).thenReturn(Optional.of(entity));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> applicationsService.createApplication(req));

        assertEquals("User has already applied for this job", ex.getMessage());
    }
}
