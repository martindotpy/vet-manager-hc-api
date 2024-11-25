package com.vet.hc.api.bill.treatmentsale.application.port.in;

import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.payload.CreateTreatmentSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a treatment sale.
 */
public interface CreateTreatmentSalePort {
    /**
     * Create a treatment sale.
     *
     * @param payload Payload with the data to create the treatment sale.
     * @return Result with the treatment sale created or the failure
     */
    Result<TreatmentSaleDto, TreatmentSaleFailure> create(CreateTreatmentSalePayload payload);
}
