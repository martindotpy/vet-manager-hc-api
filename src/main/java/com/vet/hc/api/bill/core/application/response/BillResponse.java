package com.vet.hc.api.bill.core.application.response;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class BillResponse extends ContentResponse<BillDto> {
}
