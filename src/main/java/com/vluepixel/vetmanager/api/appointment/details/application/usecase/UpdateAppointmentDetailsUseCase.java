package com.vluepixel.vetmanager.api.appointment.details.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.appointment.details.application.mapper.AppointmentDetailsMapper;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.UpdateAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.UpdateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update appointment details use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateAppointmentDetailsUseCase implements UpdateAppointmentDetailsPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;
    private final AppointmentDetailsMapper appointmentDetailsMapper;

    @Override
    @Transactional
    public AppointmentDetailsDto update(UpdateAppointmentDetailsRequest request) {
        MDC.put("operationId", "Appointment details id " + request.getId());
        log.info("Updating appointment details");

        var appointmentDetailsUpdated = appointmentDetailsMapper.fromRequest(request).build();
        appointmentDetailsUpdated = appointmentDetailsRepository.save(appointmentDetailsUpdated);

        return appointmentDetailsMapper.toDto(appointmentDetailsUpdated);
    }
}
