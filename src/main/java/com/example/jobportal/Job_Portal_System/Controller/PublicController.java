package com.example.jobportal.Job_Portal_System.Controller;

import com.example.jobportal.Job_Portal_System.DTO.UserRequestDTO;
import com.example.jobportal.Job_Portal_System.Repository.UserRepository;
import com.example.jobportal.Job_Portal_System.Utils.JwtUtils;
import com.example.jobportal.Job_Portal_System.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Public APIs")
public class PublicController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO request) {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of("APPLICANT"));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully ID->"+user,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "login via email and password")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO request) {
        Optional<User> userOpt = userRepository.findUserByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRoles());
        return ResponseEntity.ok(token);
    }
}
