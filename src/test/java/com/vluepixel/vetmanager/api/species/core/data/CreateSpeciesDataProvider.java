package com.vluepixel.vetmanager.api.species.core.data;

import static com.vluepixel.vetmanager.api.species.core.data.UpdateSpeciesDataProvider.INVALID_NAME_BLANK_UPDATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.UpdateSpeciesDataProvider.INVALID_NAME_EMPTY_UPDATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.UpdateSpeciesDataProvider.INVALID_NAME_NULL_UPDATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.UpdateSpeciesDataProvider.INVALID_NAME_TOO_LONG_UPDATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.UpdateSpeciesDataProvider.VALID_NAME_MAX_LENGTH_UPDATE_SPECIES_REQUEST;

import com.vluepixel.vetmanager.api.patient.species.domain.request.CreateSpeciesRequest;

/**
 * Create species data provider.
 */
public class CreateSpeciesDataProvider {
    public static final CreateSpeciesRequest VALID_CREATE_SPECIES_REQUEST = CreateSpeciesRequest
            .builder()
            .name("Hámnster")
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------

    // Name
    public static final CreateSpeciesRequest INVALID_NAME_TOO_LONG_CREATE_SPECIES_REQUEST = CreateSpeciesRequest
            .builder()
            .name(INVALID_NAME_TOO_LONG_UPDATE_SPECIES_REQUEST.getName())
            .build();

    public static final CreateSpeciesRequest VALID_NAME_MAX_LENGTH_CREATE_SPECIES_REQUEST = CreateSpeciesRequest
            .builder()
            .name(VALID_NAME_MAX_LENGTH_UPDATE_SPECIES_REQUEST.getName())
            .build();

    public static final CreateSpeciesRequest INVALID_NAME_BLANK_CREATE_SPECIES_REQUEST = CreateSpeciesRequest
            .builder()
            .name(INVALID_NAME_BLANK_UPDATE_SPECIES_REQUEST.getName())
            .build();

    public static final CreateSpeciesRequest INVALID_NAME_EMPTY_CREATE_SPECIES_REQUEST = CreateSpeciesRequest
            .builder()
            .name(INVALID_NAME_EMPTY_UPDATE_SPECIES_REQUEST.getName())
            .build();

    public static final CreateSpeciesRequest INVALID_NAME_NULL_CREATE_SPECIES_REQUEST = CreateSpeciesRequest
            .builder()
            .name(INVALID_NAME_NULL_UPDATE_SPECIES_REQUEST.getName())
            .build();
}
