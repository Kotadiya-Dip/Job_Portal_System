package com.example.jobportal.Job_Portal_System.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;

    @Value("${sendgrid.api-key}")
    private String sendgridApiKey;

    @Value("${sendgrid.sender}")
    private String senderEmail;

    public EmailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void sendMailBasedOnStatus(String to, String subject, String templateName, Map<String, Object> variables) {
        // Render Thymeleaf template into HTML
        Context context = new Context();
        context.setVariables(variables);
        String htmlContent = templateEngine.process("emails/" + templateName, context);

        Email from = new Email(senderEmail);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            // Optional: handle non-2xx
            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid API error: " + response.getStatusCode() + " body: " + response.getBody());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to send email via SendGrid API", ex);
        }
    }
}
