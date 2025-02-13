package com.vluepixel.vetmanager.api.appointment.details.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.appointment.details.application.mapper.AppointmentDetailsMapper;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.FindAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find appointment details.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindAppointmentDetailsUseCase implements FindAppointmentDetailsPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;
    private final AppointmentDetailsMapper appointmentDetailsMapper;

    @Override
    public Paginated<AppointmentDetailsDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Criteria " + criteria);
        log.info("Finding all appointment details");

        var paginated = appointmentDetailsRepository.findPaginatedBy(criteria);

        log.info("Retrieved {} appointment details",
                fgBrightGreen(paginated.getSize()));

        return paginated.map(appointmentDetailsMapper::toDto);
    }

    @Override
    public AppointmentDetailsDto findById(Long id) {
        MDC.put("operationId", "Id " + id);
        log.info("Finding appointment details by id");

        var appointmentDetails = appointmentDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de cita", id));

        log.info("Retrieved appointment details {}", fgBrightGreen(appointmentDetails));

        return appointmentDetailsMapper.toDto(appointmentDetails);
    }
}
