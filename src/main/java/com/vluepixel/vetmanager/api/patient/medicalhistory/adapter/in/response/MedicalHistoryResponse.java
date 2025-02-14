package com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.in.response;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Medical history response.
 */
@SuperBuilder
public final class MedicalHistoryResponse extends ContentResponse<MedicalHistoryDto> {
}
