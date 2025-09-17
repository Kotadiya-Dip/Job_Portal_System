package com.example.jobportal.Job_Portal_System.Service;

import com.example.jobportal.Job_Portal_System.DTO.JobRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.JobResponseDTO;
import com.example.jobportal.Job_Portal_System.ExceptionHandler.JobNotFoundException;
import com.example.jobportal.Job_Portal_System.Repository.JobRepository;
import com.example.jobportal.Job_Portal_System.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Create job using DTO
    public JobResponseDTO createJob(JobRequestDTO jobDto) {
        if (jobRepository.findByJobTitleAndCompanyName(jobDto.getJobTitle(), jobDto.getCompanyName()).isPresent()) {
            throw new RuntimeException("Job already exists for this company");
        }

        Job job = new Job();
        job.setJobTitle(jobDto.getJobTitle());
        job.setDescription(jobDto.getDescription());
        job.setCompanyName(jobDto.getCompanyName());
//        job.setLocation(jobDto.getLocation());
//        job.setSkills(jobDto.getSkills());
//        job.setEmployerId(jobDto.getEmployerId());

        Job savedJob = jobRepository.save(job);
        return mapToResponseDto(savedJob);
    }

    // Get all jobs as list of DTOs
    public List<JobResponseDTO> getAllJob() {
        List<Job> jobs = jobRepository.findAll();
        List<JobResponseDTO> jobDtos = new ArrayList<>();
        for (Job job : jobs) {
            jobDtos.add(mapToResponseDto(job));
        }
        return jobDtos;
    }

    // Get job by ID
    public JobResponseDTO getJobById(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found"));
        return mapToResponseDto(job);
    }

    // Delete job
    public void deleteJobById(String id) {
        jobRepository.deleteById(id);
    }

    // Update job
    public JobResponseDTO updateJob(String id, JobRequestDTO jobDto) {
        Job updatedJob = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found"));

        updatedJob.setJobTitle(jobDto.getJobTitle());
        updatedJob.setDescription(jobDto.getDescription());
        updatedJob.setCompanyName(jobDto.getCompanyName());
//        updatedJob.setLocation(jobDto.getLocation());
//        updatedJob.setSkills(jobDto.getSkills());
//        updatedJob.setEmployerId(jobDto.getEmployerId());

        Job savedJob = jobRepository.save(updatedJob);
        return mapToResponseDto(savedJob);
    }
//    public List<JobResponseDTO> searchByJobTitleAndCompanyName(String jobTitle, String companyName) {
//        List<Job> jobs;
//
//        if (companyName != null && !companyName.isBlank()) {
//            jobs = jobRepository.findByJobTitleContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(jobTitle, companyName);
//        } else {
//            jobs = jobRepository.findByJobTitleContainingIgnoreCase(jobTitle);
//        }
//
//        if (jobs.isEmpty()) {
//            throw new JobNotFoundException("No jobs found matching your search.");
//        }
//
//        return jobs.stream().map(this::mapToResponseDto).toList();
//    }
    //fot counting job
    public long countJobs() {
        return jobRepository.count();
    }


    // New method: get paginated jobs
    public List<JobResponseDTO> getAllJobPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobsPage = jobRepository.findAll(pageable);
        return jobsPage.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // Search jobs with pagination
    public List<JobResponseDTO> searchJobsByTitleAndCompanyPaginated(String jobTitle, String companyName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobsPage;

        if (companyName != null && !companyName.isBlank()) {
            jobsPage = jobRepository.findByJobTitleContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(jobTitle, companyName, pageable);
        } else {
            jobsPage = jobRepository.findByJobTitleContainingIgnoreCase(jobTitle, pageable);
        }

        if (jobsPage.isEmpty()) {
            throw new JobNotFoundException("No jobs found matching your search.");
        }

        return jobsPage.stream()
                .map(this::mapToResponseDto)
                .toList();
    }



    // Mapper method
    private JobResponseDTO mapToResponseDto(Job job) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getJobTitle());
        dto.setDescription(job.getDescription());
        dto.setCompanyName(job.getCompanyName());
//        dto.setLocation(job.getLocation());
//        dto.setSkills(job.getSkills());
//        dto.setEmployerId(job.getEmployerId());
        return dto;
    }
}
