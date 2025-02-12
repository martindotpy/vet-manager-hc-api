package com.vluepixel.vetmanager.api.shared.adapter.out.service;

import org.springframework.stereotype.Service;

import com.vluepixel.vetmanager.api.shared.application.port.out.EmailSenderPort;

import lombok.RequiredArgsConstructor;

/**
 * Email sender service.
 */
@Service
@RequiredArgsConstructor
public final class EmailSenderService implements EmailSenderPort {
    @Override
    public void sendEmail(String to, String subject, String body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmail'");
    }
}
