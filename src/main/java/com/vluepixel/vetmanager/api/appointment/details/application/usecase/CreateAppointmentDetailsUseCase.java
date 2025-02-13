package com.vluepixel.vetmanager.api.appointment.details.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.appointment.details.application.mapper.AppointmentDetailsMapper;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.CreateAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.CreateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create appointment details use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateAppointmentDetailsUseCase implements CreateAppointmentDetailsPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;
    private final AppointmentDetailsMapper appointmentDetailsMapper;

    @Override
    public AppointmentDetailsDto create(CreateAppointmentDetailsRequest request) {
        MDC.put("operationId", "Appointment details type id " + request.getAppointmentTypeId());
        log.info("Creating appointment details");

        var newAppointmentDetails = appointmentDetailsMapper.fromRequest(request).build();
        newAppointmentDetails = appointmentDetailsRepository.save(newAppointmentDetails);

        return appointmentDetailsMapper.toDto(newAppointmentDetails);
    }
}
