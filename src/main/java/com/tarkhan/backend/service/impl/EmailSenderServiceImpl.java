package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.model.auth.JWTModel;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.EmailSenderService;
import com.tarkhan.backend.service.auth.util.JwtUtil;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    @Override
    public CompletableFuture<Void> sendEmailToAll(String token, String subject, String body) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            List<User> users = userRepository.findAll();

            for (User user : users) {
                if (user.getRole().equals("ADMIN")) {
                    continue;
                }

                String email = user.getEmail();
                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                    String htmlBody = "<html>" +
                            "<body>" +
                            "<h1 style='color:#4CAF50;'>Welcome to BookWave</h1>" +
                            "<p style='font-size:14px;'>Hello,</p>" +
                            "<p>" + body + "</p>" +
                            "<img src='cid:logoImage' style='width:150px; height:auto;'/>" +
                            "<p style='font-size:12px; color:#888888;'>Wishing you a great day,<br>The BookWave Team</p>"+
                            "</body>" +
                            "</html>";

                    helper.setFrom(from);
                    helper.setTo(email);
                    helper.setSubject(subject);
                    helper.setText(htmlBody, true);

                    ClassPathResource resource = new ClassPathResource("static/logo_light.png");
                    helper.addInline("logoImage", resource);

                    mailSender.send(message);

                } catch (Exception e) {
                    throw new BookWaveApiException("Failed to send email to: " + email);
                }
            }

            return CompletableFuture.completedFuture(null);

        } catch (Exception e) {
            throw new BookWaveApiException("Error occurred while sending emails: " + e.getMessage());
        }
    }

}
