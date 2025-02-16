package com.vluepixel.vetmanager.api.appointment.details.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.details.application.port.in.DeleteAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete appointment details use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteAppointmentDetailsUseCase implements DeleteAppointmentDetailsPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;

    @Override
    public void deleteById(Long id) {
        MDC.put("operationId", "Appointment details id " + id);
        log.info("Deleting appointment details by id");

        appointmentDetailsRepository.deleteById(id);

        log.info("Appointment details deleted");
    }
}
