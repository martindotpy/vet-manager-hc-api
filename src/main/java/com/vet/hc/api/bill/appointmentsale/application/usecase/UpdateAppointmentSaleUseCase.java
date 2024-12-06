package com.vet.hc.api.bill.appointmentsale.application.usecase;

import java.util.Optional;

import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.appointmentsale.adapter.out.mapper.AppointmentSaleMapper;
import com.vet.hc.api.bill.appointmentsale.application.port.in.UpdateAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.bill.appointmentsale.domain.payload.UpdateAppointmentSalePayload;
import com.vet.hc.api.bill.appointmentsale.domain.repository.AppointmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a appointmentSale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateAppointmentSaleUseCase implements UpdateAppointmentSalePort {
    private final AppointmentSaleRepository appointmentSaleRepository;
    private final AppointmentSaleMapper appointmentSaleMapper;

    @Override
    public Result<AppointmentSaleDto, AppointmentSaleFailure> update(UpdateAppointmentSalePayload payload) {
        log.info("Updating appointment sale with id `{}`", payload.getId());

        Optional<AppointmentSale> appointmentSale = appointmentSaleRepository.findById(payload.getId());

        if (appointmentSale.isEmpty()) {
            log.error("Appointment sale with id `{}` not found", payload.getId());

            return Result.failure(AppointmentSaleFailure.NOT_FOUND);
        }

        AppointmentSale appointmentSaleFound = appointmentSale.get();
        AppointmentSale appointmentSaleToUpdate = AppointmentSale.builder()
                .id(payload.getId())
                .price(payload.getPrice())
                .discount(payload.getDiscount())
                .appointment(Appointment.builder().id(payload.getAppointmentId()).build())
                .seller(appointmentSaleFound.getSeller())
                .bill(appointmentSaleFound.getBill())
                .build();

        var result = appointmentSaleRepository.save(appointmentSaleToUpdate);

        if (result.isFailure()) {
            log.error("Error updating appointment sale: {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                default -> Result.failure(AppointmentSaleFailure.UNEXPECTED);
            };
        }

        AppointmentSale appointmentSaleUpdated = result.getSuccess();

        log.info("Appointment sale with id `{}` updated", appointmentSaleUpdated.getId());

        return Result.success(appointmentSaleMapper.toDto(appointmentSaleUpdated));
    }
}
