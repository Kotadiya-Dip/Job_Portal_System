package com.example.jobportal.Job_Portal_System.Config;

import com.example.jobportal.Job_Portal_System.Service.CustomUserDetailsService;
import com.example.jobportal.Job_Portal_System.Utils.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ✅ Public endpoints
                        .requestMatchers(
                                "/auth/**",        // login + register
                                "/jobs/search/**", // job search
                                "/users/username/**", // find user by username
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 🔒 Role-based access
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/users/update/**").hasAnyAuthority("EMPLOYEE", "APPLICANT")
                        .requestMatchers("/user/paginated/**").permitAll()
                        .requestMatchers("/users/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/job/paginated/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/jobs/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/applications/paginated").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/applications").hasAnyAuthority("APPLICANT", "EMPLOYEE", "ADMIN")
                        .requestMatchers("/applications/job/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/applications/id/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/applications/user/**").hasAnyAuthority("APPLICANT", "EMPLOYEE")
                        .requestMatchers("/applications/apply").hasAnyAuthority("APPLICANT", "EMPLOYEE")

                        // 🔒 Everything else
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
