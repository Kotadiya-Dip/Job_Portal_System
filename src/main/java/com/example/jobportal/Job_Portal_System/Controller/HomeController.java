package com.example.jobportal.Job_Portal_System.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "✅ Job Portal System is running on Render!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
