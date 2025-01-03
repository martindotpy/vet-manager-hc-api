package com.vet.hc.api.bill.appointmentsale.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.appointmentsale.adapter.out.mapper.AppointmentSaleMapper;
import com.vet.hc.api.bill.appointmentsale.application.port.in.FindAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.bill.appointmentsale.domain.repository.AppointmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a appointmentSale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindAppointmentSaleUseCase implements FindAppointmentSalePort {
    private final AppointmentSaleRepository appointmentSaleRepository;
    private final AppointmentSaleMapper appointmentSaleMapper;

    @Override
    public Result<AppointmentSaleDto, AppointmentSaleFailure> findById(Long id) {
        log.info("Finding appointment sale by id `{}`", id);

        var result = appointmentSaleRepository.findById(id);

        if (result.isEmpty()) {
            log.error("AppointmentSale not found with id `{}`", id);

            return Result.failure(AppointmentSaleFailure.NOT_FOUND);
        }

        AppointmentSale appointmentSale = result.get();

        log.info("Appointment sale with id `{}` found`", appointmentSale.getId());

        return Result.success(appointmentSaleMapper.toDto(appointmentSale));
    }
}
