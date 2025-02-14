package com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.in.response;

import java.util.List;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Medical histories response.
 */
@SuperBuilder
public final class MedicalHistoriesResponse extends ContentResponse<List<MedicalHistoryDto>> {
}
