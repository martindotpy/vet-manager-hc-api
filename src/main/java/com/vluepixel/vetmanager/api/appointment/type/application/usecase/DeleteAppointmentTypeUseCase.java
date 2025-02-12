package com.vluepixel.vetmanager.api.appointment.type.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.type.application.port.in.DeleteAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete appointment type use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteAppointmentTypeUseCase implements DeleteAppointmentTypePort {
    private final AppointmentTypeRepository appointmentTypeRepository;

    @Override
    public void deleteById(Long id) {
        MDC.put("operationId", "Appointment type id " + id);
        log.info("Deleting appointment type by id");

        appointmentTypeRepository.deleteById(id);
    }
}
