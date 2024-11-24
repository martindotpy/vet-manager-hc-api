package com.vet.hc.api.patient.medicalhistory.adapter.in.response;

import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link MedicalHistoryDto}.
 */
@SuperBuilder
public final class MedicalHistoryResponse extends ContentResponse<MedicalHistoryDto> {
}
