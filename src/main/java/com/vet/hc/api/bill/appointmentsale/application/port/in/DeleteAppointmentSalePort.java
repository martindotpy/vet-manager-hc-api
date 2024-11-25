package com.vet.hc.api.bill.appointmentsale.application.port.in;

import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a appointment sale by id.
 */
public interface DeleteAppointmentSalePort {
    /**
     * Delete a appointment sale by id.
     *
     * @param id Id of the appointment sale to delete.
     * @return Result with the success or the failure
     */
    Result<Void, AppointmentSaleFailure> deleteById(Long id);
}
