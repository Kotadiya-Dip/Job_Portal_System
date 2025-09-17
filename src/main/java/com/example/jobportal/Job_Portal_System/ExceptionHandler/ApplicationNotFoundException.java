package com.example.jobportal.Job_Portal_System.ExceptionHandler;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
