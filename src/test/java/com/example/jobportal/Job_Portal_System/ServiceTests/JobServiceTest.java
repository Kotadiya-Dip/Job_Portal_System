package com.example.jobportal.Job_Portal_System.ServiceTests;

import com.example.jobportal.Job_Portal_System.DTO.JobRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.JobResponseDTO;
import com.example.jobportal.Job_Portal_System.Repository.JobRepository;
import com.example.jobportal.Job_Portal_System.Service.JobService;
import com.example.jobportal.Job_Portal_System.entity.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createJob_Success() {
        JobRequestDTO dto = new JobRequestDTO("Java Dev", "Backend job", "ABC Corp");
        Job entity = new Job("1", "Java Dev", "Backend job", "ABC Corp");

        when(jobRepository.findByJobTitleAndCompanyName("Java Dev", "ABC Corp")).thenReturn(Optional.empty());
        when(jobRepository.save(any(Job.class))).thenReturn(entity);

        JobResponseDTO result = jobService.createJob(dto);

        assertEquals("Java Dev", result.getTitle());
        assertEquals("ABC Corp", result.getCompanyName());
    }

    @Test
    void createJob_Duplicate() {
        JobRequestDTO dto = new JobRequestDTO("Java Dev", "Backend job", "ABC Corp");
        Job entity = new Job("1", "Java Dev", "Backend job", "ABC Corp");

        when(jobRepository.findByJobTitleAndCompanyName("Java Dev", "ABC Corp")).thenReturn(Optional.of(entity));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> jobService.createJob(dto));

        assertEquals("Job already exists for this company", ex.getMessage());
    }
}
