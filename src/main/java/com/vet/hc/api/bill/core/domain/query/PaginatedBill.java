package com.vet.hc.api.bill.core.domain.query;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.shared.domain.query.Paginated;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedBill extends Paginated<BillDto> {
}
