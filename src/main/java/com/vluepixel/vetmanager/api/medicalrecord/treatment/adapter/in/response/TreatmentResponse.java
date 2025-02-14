package com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.in.response;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto.TreatmentDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Treatment response.
 */
@SuperBuilder
public final class TreatmentResponse extends ContentResponse<TreatmentDto> {
}
