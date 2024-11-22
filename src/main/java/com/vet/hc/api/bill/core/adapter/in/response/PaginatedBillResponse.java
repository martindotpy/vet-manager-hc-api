package com.vet.hc.api.bill.core.adapter.in.response;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link BillDto} paginated.
 */
@SuperBuilder
public final class PaginatedBillResponse extends PaginatedResponse<BillDto> {
}
