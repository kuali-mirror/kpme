package org.kuali.hr.core.notification.service;

public interface KPMENotificationService {

	void sendNotification(String subject, String message, String... principalIds);

}