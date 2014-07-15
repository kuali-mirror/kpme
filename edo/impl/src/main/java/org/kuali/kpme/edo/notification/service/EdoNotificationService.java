package org.kuali.kpme.edo.notification.service;

public interface EdoNotificationService {
    public void sendMail(String toAddress, String fromAddress, String subject, String content);
}

