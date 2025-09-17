package com.example.jobportal.Job_Portal_System.Repository;

import com.example.jobportal.Job_Portal_System.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends MongoRepository<Job, String> {


    Optional<Job> findByJobTitleAndCompanyName(String jobTitle, String companyName);


    Optional<Job> findByJobTitle(String jobTitle);
    Page<Job> findByJobTitleContainingIgnoreCase(String jobTitle,Pageable pageable);


    Page<Job> findByJobTitleContainingIgnoreCaseAndCompanyNameContainingIgnoreCase(String jobTitle, String companyName,Pageable pageable
    );

}
