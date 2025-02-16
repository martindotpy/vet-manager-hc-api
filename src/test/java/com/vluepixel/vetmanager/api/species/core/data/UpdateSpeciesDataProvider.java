package com.vluepixel.vetmanager.api.species.core.data;

import com.vluepixel.vetmanager.api.patient.species.domain.request.UpdateSpeciesRequest;

/**
 * Update species data provider.
 */
public class UpdateSpeciesDataProvider {
    public static final UpdateSpeciesRequest INVALID_ID_NOT_FOUND_UPDATE_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(10)
            .name("Hámnster")
            .build();

    public static final UpdateSpeciesRequest INVALID_ID_NEGATIVE_UPDATE_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(-10)
            .name("Hámnster")
            .build();

    public static final UpdateSpeciesRequest VALID_UPDATE_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(1)
            .name("Super perro")
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------

    // Name
    public static final int MAX_NAME_LENGTH = 50;
    public static final UpdateSpeciesRequest INVALID_NAME_TOO_LONG_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(1)
            .name("a".repeat(MAX_NAME_LENGTH + 1))
            .build();

    public static final UpdateSpeciesRequest VALID_NAME_MAX_LENGTH_UPDATE_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(1)
            .name("a".repeat(MAX_NAME_LENGTH))
            .build();

    public static final UpdateSpeciesRequest INVALID_NAME_BLANK_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(1)
            .name(" ")
            .build();

    public static final UpdateSpeciesRequest INVALID_NAME_EMPTY_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(1)
            .name("")
            .build();

    public static final UpdateSpeciesRequest INVALID_NAME_NULL_SPECIES_REQUEST = UpdateSpeciesRequest
            .builder()
            .id(1)
            .name("")
            .build();
}
