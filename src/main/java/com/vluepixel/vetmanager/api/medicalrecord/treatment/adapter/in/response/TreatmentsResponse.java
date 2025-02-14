package com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.in.response;

import java.util.List;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto.TreatmentDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Treatments response.
 */
@SuperBuilder
public final class TreatmentsResponse extends ContentResponse<List<TreatmentDto>> {
}
