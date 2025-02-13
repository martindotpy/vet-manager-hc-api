package com.vluepixel.vetmanager.api.appointment.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.appointment.core.application.mapper.AppointmentMapper;
import com.vluepixel.vetmanager.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.domain.repository.AppointmentRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find appointment.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindAppointmentUseCase implements FindAppointmentPort {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public Paginated<AppointmentDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Criteria " + criteria);
        log.info("Finding all appointment");

        var paginated = appointmentRepository.findPaginatedBy(criteria);

        log.info("Retrieved {} appointment ",
                fgBrightGreen(paginated.getSize()));

        return paginated.map(appointmentMapper::toDto);
    }

    @Override
    public AppointmentDto findById(Long id) {
        MDC.put("operationId", "Id " + id);
        log.info("Finding appointment  by id");

        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de cita", id));

        log.info("Retrieved appointment  {}", fgBrightGreen(appointment));

        return appointmentMapper.toDto(appointment);
    }
}
