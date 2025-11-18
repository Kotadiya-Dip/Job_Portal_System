package com.example.jobportal.Job_Portal_System.Repository;

import com.example.jobportal.Job_Portal_System.entity.Applications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationsRepository extends MongoRepository<Applications, String> {

    Page<Applications> findByUserId(String userId, Pageable pageable);
    Page<Applications> findByJobId(String jobId,Pageable pageable);
    Optional<Applications> findByUserIdAndJobId(String userId, String jobId);

}
