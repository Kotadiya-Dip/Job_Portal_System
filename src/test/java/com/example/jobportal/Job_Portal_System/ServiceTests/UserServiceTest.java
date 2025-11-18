package com.example.jobportal.Job_Portal_System.ServiceTests;

import com.example.jobportal.Job_Portal_System.DTO.UserRequestDTO;
import com.example.jobportal.Job_Portal_System.DTO.UserResponseDTO;
import com.example.jobportal.Job_Portal_System.Repository.UserRepository;
import com.example.jobportal.Job_Portal_System.Service.UserService;
import com.example.jobportal.Job_Portal_System.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        UserRequestDTO request = new UserRequestDTO("testUser", "test@gmail.com", "pass123");
        User entity = new User("1", "testUser", "test@gmail.com", "pass123", List.of("APPLICANT"));

        when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(entity);

        UserResponseDTO response = userService.saveUser(request);

        assertEquals("test@gmail.com", response.getEmail());
        assertTrue(response.getRoles().contains("APPLICANT"));
    }

    @Test
    void registerUser_DuplicateEmail() {
        UserRequestDTO request = new UserRequestDTO("testUser", "test@gmail.com", "pass123");
        User entity = new User("1", "testUser", "test@gmail.com", "pass123", List.of("APPLICANT"));

        when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(Optional.of(entity));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.saveUser(request));

        assertEquals("Email already exists", ex.getMessage());
    }
}
