//package com.example.jobportal.Job_Portal_System.EmailTests;
//
//import com.example.jobportal.Job_Portal_System.Service.EmailService;
//import jakarta.mail.internet.MimeMessage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//class EmailServiceTest {
//
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    @Mock
//    private TemplateEngine templateEngine;
//
//    @InjectMocks
//    private EmailService emailService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSendMailBasedOnStatus() throws Exception {
//        // Arrange
//        String to = "dipkotadiya187@gmail.com";
//        String subject = "Job Status Update";
//        String templateName = "status-template";
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("username", "Dip");
//
//        MimeMessage mimeMessage = mock(MimeMessage.class);
//
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//        when(templateEngine.process(eq("emails/" + templateName), any(Context.class)))
//                .thenReturn("<html><body>Hello Dip</body></html>");
//
//        // Act
////        emailService.sendMailBasedOnStatus(to, subject, templateName, variables);
//        emailService.sendMailBasedOnStatus(to, subject, templateName, variables);
//
//        // Assert
//        verify(javaMailSender, times(1)).send(mimeMessage);
//        verify(templateEngine, times(1)).process(eq("emails/" + templateName), any(Context.class));
//    }
//}
