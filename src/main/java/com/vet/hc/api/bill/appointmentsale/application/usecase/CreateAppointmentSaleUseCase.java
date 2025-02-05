package com.vet.hc.api.bill.appointmentsale.application.usecase;

import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.appointmentsale.adapter.out.mapper.AppointmentSaleMapper;
import com.vet.hc.api.bill.appointmentsale.application.port.in.CreateAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.bill.appointmentsale.domain.payload.CreateAppointmentSalePayload;
import com.vet.hc.api.bill.appointmentsale.domain.repository.AppointmentSaleRepository;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.model.UserImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a appointment sale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateAppointmentSaleUseCase implements CreateAppointmentSalePort {
    private final AppointmentSaleRepository appointmentSaleRepository;
    private final AppointmentSaleMapper appointmentSaleMapper;

    @Override
    public Result<AppointmentSaleDto, AppointmentSaleFailure> create(CreateAppointmentSalePayload payload) {
        AppointmentSale appointmentSale = AppointmentSale.builder()
                .price(payload.getPrice())
                .discount(payload.getDiscount())
                .appointment(Appointment.builder().id(payload.getAppointmentId()).build())
                .seller(UserImpl.builder().id(payload.getSellerId()).build())
                .bill(Bill.builder().id(payload.getBillId()).build())
                .build();

        var result = appointmentSaleRepository.save(appointmentSale);

        if (result.isFailure()) {
            return Result.failure(AppointmentSaleFailure.UNEXPECTED);
        }

        AppointmentSale createdAppointmentSale = result.getSuccess();

        log.info("Appointment sale with bill id `{}` created", payload.getBillId());

        return Result.success(appointmentSaleMapper.toDto(createdAppointmentSale));
    }
}
