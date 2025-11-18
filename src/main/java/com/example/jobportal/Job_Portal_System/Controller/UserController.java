    package com.example.jobportal.Job_Portal_System.Controller;

    import com.example.jobportal.Job_Portal_System.DTO.*;
    import com.example.jobportal.Job_Portal_System.Repository.UserRepository;
    import com.example.jobportal.Job_Portal_System.Service.UserService;
    import com.example.jobportal.Job_Portal_System.DTO.UserResponseDTO;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.security.core.Authentication;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/users")
    @RequiredArgsConstructor
    @Tag(name = "User APIs")
    public class UserController {

        @Autowired
        private  UserService userService;

        @Autowired
        private UserRepository userRepository;

    //    // ✅ Save new user
    //    @PostMapping("/new")
    //    public ResponseEntity<Boolean> saveNewUser(@Valid @RequestBody UserRequestDTO userDto) {
    //        return ResponseEntity.ok(userService.saveNewUser(userDto));
    //    }
    //
    //    // ✅ Save user and return DTO
    //    @PostMapping
    //    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserRequestDTO userDto) {
    //        return ResponseEntity.ok(userService.saveUser(userDto));
    //    }

        // ✅ Return list of DTOs

        @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
        @GetMapping
        @Operation(summary = "Get all user Only Employee and Admin")
        public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
            return ResponseEntity.ok(userService.getAllUsers());
        }

        // ✅ Return single DTO
        @GetMapping("id/{id}")
        @Operation(summary = "find User by Id")
        public ResponseEntity<Optional<UserResponseDTO>> findById(@PathVariable String id) {
            return ResponseEntity.ok(userService.findById(id));
        }

        @PreAuthorize("hasAuthority('APPLICANT')")
        @PutMapping("/update/{id}")
        @Operation(summary = "Update user by id")
        public ResponseEntity<?> updateUserById(
                @PathVariable String id,
                @Valid @RequestBody UserUpdateRequest request,
                Authentication authentication) {
            try {
                UserResponseDTO updated = userService.updateUserById(id, request, authentication);
                return ResponseEntity.ok(updated);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }





        @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
        @DeleteMapping("id/{id}")
        @Operation(summary = "delete user by id")
        public ResponseEntity<Void> deleteUser(@PathVariable String id) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }


        @GetMapping("email/{email}")
        @Operation(summary = "find user by entering Email")
        public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
            return ResponseEntity.ok(userService.findUserByEmail(email));
        }


        @GetMapping("username/{userName}")
        @Operation(summary = "find user by User name")
        public ResponseEntity<?> findUserByUserName(@PathVariable String userName) {
            return ResponseEntity.ok(userService.findUserByUserName(userName));
        }
    }
