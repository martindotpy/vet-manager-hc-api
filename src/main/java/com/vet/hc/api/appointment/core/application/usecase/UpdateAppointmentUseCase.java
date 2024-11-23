package com.vet.hc.api.appointment.core.application.usecase;

import java.util.Optional;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.application.port.in.UpdateAppointmentPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.core.domain.payload.UpdateAppointmentPayload;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update an appointment .
 */
@Slf4j
@NoArgsConstructor
public final class UpdateAppointmentUseCase implements UpdateAppointmentPort {
    private AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper = AppointmentMapper.INSTANCE;

    @Inject
    public UpdateAppointmentUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Result<AppointmentDto, AppointmentFailure> update(UpdateAppointmentPayload payload) {
        log.info("Updating appointment with id {}", payload.getId());

        Optional<Appointment> appointment = appointmentRepository.findById(payload.getId());

        if (appointment.isEmpty()) {
            log.error("Appointment with id {} not found", payload.getId());

            return Result.failure(AppointmentFailure.NOT_FOUND);
        }

        Appointment appointmentFound = appointment.get();
        Appointment appointmentToUpdate = Appointment.builder()
                .id(payload.getId())
                .startAt(payload.getStartAt())
                .description(payload.getDescription())
                .details(appointmentFound.getDetails())
                .patient(Patient.builder()
                        .id(appointmentFound.getPatient().getId())
                        .build())
                .vet(appointmentFound.getVet())
                .build();

        var result = appointmentRepository.save(appointmentToUpdate);

        if (result.isFailure()) {
            log.error("Error updating appointment : {}", result.getFailure());

            return Result.failure(AppointmentFailure.UNEXPECTED);
        }

        Appointment appointmentUpdated = result.getSuccess();

        log.info("Appointment with id {} updated", appointmentUpdated.getId());

        return Result.success(appointmentMapper.toDto(appointmentUpdated));
    }
}
