package com.example.jobportal.Job_Portal_System.Repository;

import com.example.jobportal.Job_Portal_System.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User ,String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserName(String userName);

    Optional<User> findByUserName(String userName);

    long countByRolesContaining(String role);


}