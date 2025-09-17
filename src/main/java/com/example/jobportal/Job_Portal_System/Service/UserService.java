package com.example.jobportal.Job_Portal_System.Service;

import com.example.jobportal.Job_Portal_System.DTO.UserRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.UserResponseDTO;
import com.example.jobportal.Job_Portal_System.DTO.UserUpdateRequest;
import com.example.jobportal.Job_Portal_System.ExceptionHandler.JobNotFoundException;
import com.example.jobportal.Job_Portal_System.ExceptionHandler.UserNotFoundException;
import com.example.jobportal.Job_Portal_System.Repository.UserRepository;
import com.example.jobportal.Job_Portal_System.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private Authentication authentication;

    // ✅ Save new user (return boolean)
    public boolean saveNewUser(UserRequestDTO userDto) {
        try {
            User user = new User();
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setRoles(Arrays.asList("CANDIDATE"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Save & return response DTO
    public UserResponseDTO saveUser(UserRequestDTO userDto) {
        if (userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findUserByUserName(userDto.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(Arrays.asList("CANDIDATE"));

        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }

    // ✅ Get all users without stream
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> dtoList = new ArrayList<>();

        for (User user : users) {
            UserResponseDTO dto = mapToResponseDto(user);
            dtoList.add(dto);
        }

        return dtoList;
    }

    // ✅ Return Optional<UserResponseDTO>
    public Optional<UserResponseDTO> findById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return Optional.ofNullable(Optional.of(mapToResponseDto(user.get())).
                    orElseThrow(() -> new UserNotFoundException("User not found with id: " + id)));
        }
        return Optional.empty();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // keep old find methods unchanged
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    public UserResponseDTO updateUserById(String id, UserUpdateRequest request, Authentication authentication) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        String loggedInEmail = authentication.getName();

        // ✅ Case 1: Logged-in user trying to update someone else's profile
        if (!loggedInEmail.equals(user.getEmail())) {
            if (user.isRequiresReLogin() && loggedInEmail.equals(user.getOldEmail())) {
                // User just changed email, token still has old email
                throw new RuntimeException("Re-login to continue");
            }
            throw new JobNotFoundException("Job not found with id: " + id);
        }

        // ✅ Case 2: Handle email change
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            user.setOldEmail(user.getEmail());       // store old email
            user.setEmail(request.getEmail());       // update to new email
            user.setRequiresReLogin(true);           // mark user as needing re-login
            userRepository.save(user);
            throw new RuntimeException("Email updated successfully. Please re-login to continue.");
        }

        // ✅ Case 3: Check requires re-login flag before other updates
        if (user.isRequiresReLogin()) {
            throw new RuntimeException("Re-login to continue");
        }

        // ✅ Case 4: Update other fields
        if (request.getUserName() != null) user.setUserName(request.getUserName());

        // ✅ Handle password change
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            if (request.getPassword() == null || request.getPassword().isBlank()) {
                throw new RuntimeException("Old password is required when changing password");
            }
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Old password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        User saved = userRepository.save(user);
        return mapToResponseDto(saved);
    }
    //for counting users
    public long countUsers() {
        return userRepository.count();
    }

    public long countByRole(String roles) {
        return userRepository.countByRolesContaining(roles);
    }



    // ✅ Mapper
    private UserResponseDTO mapToResponseDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
