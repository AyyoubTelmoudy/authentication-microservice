package com.emsi.pfe.service;

public interface MailingService {
    void sendMail(String toMail,
                  String subject,
                  String body);
}
