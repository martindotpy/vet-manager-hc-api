package com.vet.hc.api.appointment.core.application.usecase;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.query.PaginatedAppointment;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find an appointment .
 */
@Slf4j
@NoArgsConstructor
public final class FindAppointmentUseCase implements FindAppointmentPort {
    private AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper = AppointmentMapper.INSTANCE;

    @Inject
    public FindAppointmentUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Result<PaginatedAppointment, AppointmentFailure> match(Criteria criteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'match'");
    }

    @Override
    public Result<AppointmentDto, AppointmentFailure> findById(Long id) {
        log.info("Finding appointment by id: {}", id);

        var result = appointmentRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Appointment not found with id: {}", id);

            return Result.failure(AppointmentFailure.NOT_FOUND);
        }

        log.info("Appointment found: {}", result.get());

        return Result.success(appointmentMapper.toDto(result.get()));
    }
}
