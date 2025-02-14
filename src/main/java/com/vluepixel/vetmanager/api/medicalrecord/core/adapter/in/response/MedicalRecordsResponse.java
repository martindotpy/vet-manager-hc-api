package com.vluepixel.vetmanager.api.medicalrecord.core.adapter.in.response;

import java.util.List;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Medical records response.
 */
@SuperBuilder
public final class MedicalRecordsResponse extends ContentResponse<List<MedicalRecordDto>> {
}
