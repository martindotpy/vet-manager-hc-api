package com.vluepixel.vetmanager.api.patient.core.adapter.in.response;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Patient response.
 */
@SuperBuilder
public final class PatientResponse extends ContentResponse<PatientDto> {
}
