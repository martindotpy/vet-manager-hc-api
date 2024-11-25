package com.vet.hc.api.bill.treatmentsale.adapter.in.response;

import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link TreatmentSaleDto}.
 */
@SuperBuilder
public final class TreatmentSaleResponse extends ContentResponse<TreatmentSaleDto> {
}
