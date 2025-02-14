package com.vluepixel.vetmanager.api.vaccine.core.adapter.in.response;

import java.util.List;

import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;

import lombok.experimental.SuperBuilder;

/**
 * Vaccines response.
 */
@SuperBuilder
public final class VaccinesResponse extends ContentResponse<List<VaccineDto>> {
}
