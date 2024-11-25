package com.vet.hc.api.medicalrecord.core.adapter.in.response;

import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link MedicalRecordDto}.
 */
@SuperBuilder
public final class MedicalRecordResponse extends ContentResponse<MedicalRecordDto> {
}
