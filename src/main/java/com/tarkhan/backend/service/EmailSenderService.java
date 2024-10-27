package com.tarkhan.backend.service;

import java.util.concurrent.CompletableFuture;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String body);
    CompletableFuture<Void> sendEmailToAll(String token, String subject, String body);
}
