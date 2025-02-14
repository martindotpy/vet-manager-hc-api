package com.vluepixel.vetmanager.api.medicalrecord.core.adapter.in.response;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Medical record response.
 */
@SuperBuilder
public final class MedicalRecordResponse extends ContentResponse<MedicalRecordDto> {
}
