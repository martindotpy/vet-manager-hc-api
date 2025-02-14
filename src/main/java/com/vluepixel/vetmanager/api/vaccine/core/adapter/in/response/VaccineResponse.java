package com.vluepixel.vetmanager.api.vaccine.core.adapter.in.response;

import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;

import lombok.experimental.SuperBuilder;

/**
 * Vaccine response.
 */
@SuperBuilder
public final class VaccineResponse extends ContentResponse<VaccineDto> {
}
