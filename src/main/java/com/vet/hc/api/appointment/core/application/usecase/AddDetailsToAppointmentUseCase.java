package com.vet.hc.api.appointment.core.application.usecase;

import com.vet.hc.api.appointment.core.application.port.in.AddDetailsToAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.details.application.port.in.CreateAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.payload.CreateAppointmentDetailsPayload;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public final class AddDetailsToAppointmentUseCase implements AddDetailsToAppointmentPort {
    private FindAppointmentPort findAppointmentPort;
    private CreateAppointmentDetailsPort createAppointmentDetailsPort;

    @Inject
    public AddDetailsToAppointmentUseCase(
            FindAppointmentPort findAppointmentPort,
            CreateAppointmentDetailsPort createAppointmentDetailsPort) {
        this.findAppointmentPort = findAppointmentPort;
        this.createAppointmentDetailsPort = createAppointmentDetailsPort;
    }

    @Override
    public Result<AppointmentDto, AppointmentFailure> add(CreateAppointmentDetailsPayload payload) {
        log.info("Adding details to appointment: {}", payload);

        var result = createAppointmentDetailsPort.create(payload);

        if (result.isFailure()) {
            log.error("Failed to add details to appointment: {}", result.getFailure());

            return Result.failure(AppointmentFailure.UNEXPECTED);
        }

        var resultAppointment = findAppointmentPort.findById(payload.getAppointmentId());

        if (resultAppointment.isFailure()) {
            log.error("Failed to find appointment with id: {}", payload.getAppointmentId());

            return Result.failure(AppointmentFailure.NOT_FOUND);
        }

        log.info("Details added to appointment: {}", payload);

        return Result.success(resultAppointment.getSuccess());
    }
}
