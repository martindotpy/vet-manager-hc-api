package com.vet.hc.api.bill.core.adapter.in.response;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link BillDto}.
 */
@SuperBuilder
public final class BillResponse extends ContentResponse<BillDto> {
}
