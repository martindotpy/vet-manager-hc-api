package com.vet.hc.api.appointment.details.application.usecase;

import com.vet.hc.api.appointment.details.adapter.out.mapper.AppointmentDetailsMapper;
import com.vet.hc.api.appointment.details.application.port.in.FindAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find an appointment details.
 */
@Slf4j
@NoArgsConstructor
public final class FindAppointmentDetailsUseCase implements FindAppointmentDetailsPort {
    private AppointmentDetailsRepository appointmentDetailsRepository;

    private final AppointmentDetailsMapper appointmentDetailsMapper = AppointmentDetailsMapper.INSTANCE;

    @Inject
    public FindAppointmentDetailsUseCase(AppointmentDetailsRepository appointmentDetailsRepository) {
        this.appointmentDetailsRepository = appointmentDetailsRepository;
    }

    @Override
    public Result<AppointmentDetailsDto, AppointmentDetailsFailure> findById(Long id) {
        log.info("Finding appointment details by id: {}", id);

        var result = appointmentDetailsRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Appointment details not found with id: {}", id);

            return Result.failure(AppointmentDetailsFailure.NOT_FOUND);
        }

        log.info("Appointment details found: {}", result.get());

        return Result.success(appointmentDetailsMapper.toDto(result.get()));
    }
}
