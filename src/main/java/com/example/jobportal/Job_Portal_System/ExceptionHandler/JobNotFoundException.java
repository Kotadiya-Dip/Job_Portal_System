package com.example.jobportal.Job_Portal_System.ExceptionHandler;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String message) {
        super(message);
    }
}
