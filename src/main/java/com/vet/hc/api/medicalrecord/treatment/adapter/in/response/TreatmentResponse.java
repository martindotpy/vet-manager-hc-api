package com.vet.hc.api.medicalrecord.treatment.adapter.in.response;

import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link TreatmentDto}.
 */
@SuperBuilder
public final class TreatmentResponse extends ContentResponse<TreatmentDto> {
}
