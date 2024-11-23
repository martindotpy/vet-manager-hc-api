package com.vet.hc.api.appointment.core.application.usecase;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.application.port.in.CreateAppointmentPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.core.domain.payload.CreateAppointmentPayload;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.auth.core.application.port.in.GetAuthenticatedUserPort;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an appointment .
 */
@Slf4j
@NoArgsConstructor
public final class CreateAppointmentUseCase implements CreateAppointmentPort {
    private GetAuthenticatedUserPort getAuthenticatedUserPort;
    private AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper = AppointmentMapper.INSTANCE;

    @Inject
    public CreateAppointmentUseCase(
            GetAuthenticatedUserPort getAuthenticatedUserPort,
            AppointmentRepository appointmentRepository) {
        this.getAuthenticatedUserPort = getAuthenticatedUserPort;
        this.appointmentRepository = appointmentRepository;
    }

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
            log.error("Failed to create appointment : {}",
                    result.getFailure().getMessage());

            return Result.failure(AppointmentFailure.UNEXPECTED);
        }

        Appointment createdAppointment = result.getSuccess();

        log.info("Appointment created: {}", createdAppointment.getId());

        return Result.success(appointmentMapper.toDto(createdAppointment));
    }
}
