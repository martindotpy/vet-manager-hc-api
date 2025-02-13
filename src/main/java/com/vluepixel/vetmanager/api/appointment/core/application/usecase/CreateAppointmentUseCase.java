package com.vluepixel.vetmanager.api.appointment.core.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.appointment.core.application.mapper.AppointmentMapper;
import com.vluepixel.vetmanager.api.appointment.core.application.port.in.CreateAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.domain.repository.AppointmentRepository;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.CreateAppointmentRequest;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create appointment use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateAppointmentUseCase implements CreateAppointmentPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional
    public AppointmentDto create(CreateAppointmentRequest request) {
        MDC.put("operationId", "Appointment start at " + request.getStartAt());
        log.info("Creating appointment");

        var newAppointment = appointmentMapper.fromRequest(request).build();

        // Save the details
        newAppointment.getDetails().forEach(appointmentDetailsRepository::save);

        // Save the appointment
        newAppointment = appointmentRepository.save(newAppointment);

        return appointmentMapper.toDto(newAppointment);
    }
}
