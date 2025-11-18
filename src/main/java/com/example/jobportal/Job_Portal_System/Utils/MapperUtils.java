package com.example.jobportal.Job_Portal_System.Utils;

import com.example.jobportal.Job_Portal_System.DTO.*;
import com.example.jobportal.Job_Portal_System.entity.User;
import com.example.jobportal.Job_Portal_System.entity.Job;
import com.example.jobportal.Job_Portal_System.entity.Applications;

public final class MapperUtils {

    private MapperUtils() {}

    // User DTO mapping
    public static User toUserEntity(UserRequestDTO dto) {
        User u = new User();
        u.setUserName(dto.getUserName());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        return u;
    }

    public static UserResponseDTO toUserResponse(User u) {
        if (u == null) return null;
        return new UserResponseDTO(u.getId(), u.getUserName(), u.getEmail(), u.getRoles());
    }

    // Job DTO mapping
    public static Job toJobEntity(JobRequestDTO dto) {
        Job j = new Job();
        j.setJobTitle(dto.getJobTitle());
        j.setDescription(dto.getDescription());
        j.setCompanyName(dto.getCompanyName());
        return j;
    }

    public static JobResponseDTO toJobResponse(Job j) {
        if (j == null) return null;
        return new JobResponseDTO(j.getId(), j.getJobTitle(), j.getDescription(), j.getCompanyName());
    }

    // Application -> Response DTO mapping
    public static ApplicationResponseDTO toApplicationResponse(Applications a, User user, Job job) {
        if (a == null) return null;

        return new ApplicationResponseDTO(
                a.getId(),
                a.getStatus(),
                a.getResume(),
                toUserResponse(user),
                job
//                (user != null ? user.getUserName() : null),   // set applicantName
//                (job != null ? job.getJobTitle() : null)        // set jobTitle
        );
    }

}
