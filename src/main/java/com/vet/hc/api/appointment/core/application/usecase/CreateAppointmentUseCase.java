package com.vet.hc.api.appointment.core.application.usecase;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.application.port.in.CreateAppointmentPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.core.domain.payload.CreateAppointmentPayload;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.auth.core.application.port.in.GetAuthenticatedUserPort;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an appointment .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateAppointmentUseCase implements CreateAppointmentPort {
    private final GetAuthenticatedUserPort getAuthenticatedUserPort;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public Result<AppointmentDto, AppointmentFailure> create(CreateAppointmentPayload payload) {
        log.info("Creating appointment with patient id: {}", payload.getPatientId());

        Appointment appointment = Appointment.builder()
                .startAt(payload.getStartAt())
                .description(payload.getDescription())
                .patient(Patient.builder()
                        .id(payload.getPatientId())
                        .build())
                .vet(User.builder()
                        .id(getAuthenticatedUserPort.get().get().getId())
                        .build())
                .build();

        var result = appointmentRepository.save(appointment);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            if (failure == RepositoryFailure.ENTITY_NOT_FOUND) {
                if (failure.getField().equals("patient")) {
                    log.error("Patient not found: {}", payload.getPatientId());

                    return Result.failure(AppointmentFailure.PATIENT_NOT_FOUND);
                }

                log.error("Field not found: {}", failure.getField());

                return Result.failure(AppointmentFailure.FIELD_NOT_FOUND);
            }

            log.error("Failed to create appointment : {}",
                    result.getFailure().getMessage());

            return Result.failure(AppointmentFailure.UNEXPECTED);
        }

        Appointment createdAppointment = result.getSuccess();

        log.info("Appointment created: {}", createdAppointment.getId());

        return Result.success(appointmentMapper.toDto(createdAppointment));
    }
}
