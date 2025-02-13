package com.vluepixel.vetmanager.api.appointment.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.appointment.core.application.mapper.AppointmentMapper;
import com.vluepixel.vetmanager.api.appointment.core.application.port.in.UpdateAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.domain.model.Appointment;
import com.vluepixel.vetmanager.api.appointment.core.domain.repository.AppointmentRepository;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.UpdateAppointmentRequest;
import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.InnerEntityDoNotBelongToEntity;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update appointment use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateAppointmentUseCase implements UpdateAppointmentPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional
    public AppointmentDto update(UpdateAppointmentRequest request) {
        MDC.put("operationId", "Appointment id " + request.getId());
        log.info("Updating appointment");

        // Verify if the appointment details are valid
        var appointmentToUpdate = appointmentRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(Appointment.class, request.getId()));
        var appointmentUpdated = appointmentMapper.fromRequest(request).build();
        var streamPreviousDetails = appointmentToUpdate.getDetails().stream();

        for (AppointmentDetails updatedDetails : appointmentUpdated.getDetails()) {
            if (!streamPreviousDetails.anyMatch(d -> d.getId().equals(updatedDetails.getId()))) {
                throw new InnerEntityDoNotBelongToEntity(
                        "details", "El detalle con id " + updatedDetails.getId() + " no pertenece a la cita");
            }
        }

        // Update the details
        appointmentUpdated.getDetails().forEach(appointmentDetailsRepository::save);

        // Update the appointment
        appointmentUpdated = appointmentRepository.save(appointmentUpdated);

        return appointmentMapper.toDto(appointmentUpdated);
    }
}
