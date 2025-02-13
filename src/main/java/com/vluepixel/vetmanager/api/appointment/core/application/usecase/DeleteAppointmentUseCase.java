package com.vluepixel.vetmanager.api.appointment.core.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.core.application.port.in.DeleteAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.domain.repository.AppointmentRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete appointment use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteAppointmentUseCase implements DeleteAppointmentPort {
    private final AppointmentRepository appointmentRepository;

    @Override
    public void deleteById(Long id) {
        MDC.put("operationId", "Appointment id " + id);
        log.info("Deleting appointment by id");

        appointmentRepository.deleteById(id);
    }
}
