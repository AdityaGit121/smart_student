package com.smartstudent.sis.service.impl;

import com.smartstudent.sis.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation of NotificationService.
 * In a production system, this sends real SMTP emails.
 * Here, it simulates message dispatching with detailed logging.
 */
@Service
public class EmailNotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    @Override
    public void sendStudentNotification(String recipientEmail, String subject, String messageContent) {
        logger.info("[NOTIFICATION DISPATCHED] To: '{}' | Subject: '{}' | Content: {}",
                recipientEmail, subject, messageContent);
    }
}
