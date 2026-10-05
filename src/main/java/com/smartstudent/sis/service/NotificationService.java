package com.smartstudent.sis.service;

/**
 * Service interface for dispatching notifications (Email/SMS/System alerts).
 */
public interface NotificationService {

    void sendStudentNotification(String recipientEmail, String subject, String messageContent);
}
