package com.vluepixel.vetmanager.api.shared.application.port.out;

/**
 * Email sender port.
 */
public interface EmailSenderPort {
    /**
     * Sends an email.
     *
     * @param to      the recipient.
     * @param subject the email subject.
     * @param body    the email body.
     */
    void sendEmail(String to, String subject, String body);
}
