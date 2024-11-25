package com.vet.hc.api.bill.core.application.response;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedBillResponse extends PaginatedResponse<BillDto> {
}
