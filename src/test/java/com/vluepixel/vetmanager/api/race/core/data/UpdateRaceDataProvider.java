package com.vluepixel.vetmanager.api.race.core.data;

import com.vluepixel.vetmanager.api.patient.race.domain.request.UpdateRaceRequest;

/**
 * Update race data provider.
 */
public class UpdateRaceDataProvider {

    public static final UpdateRaceRequest VALID_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(1)
            .name("Pequines")
            .speciesId(1)
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------

    // ID
    public static final UpdateRaceRequest INVALID_ID_NOT_FOUND_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(10)
            .name(VALID_UPDATE_RACE_REQUEST.getName())
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final UpdateRaceRequest INVALID_ID_NEGATIVE_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(-1)
            .name(VALID_UPDATE_RACE_REQUEST.getName())
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final UpdateRaceRequest INVALID_ID_NULL_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(null)
            .name(VALID_UPDATE_RACE_REQUEST.getName())
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    // Name
    private static final int MAX_NAME_LENGTH = 50;
    public static final UpdateRaceRequest INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name("a".repeat(MAX_NAME_LENGTH + 1))
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final UpdateRaceRequest VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name("a".repeat(MAX_NAME_LENGTH))
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final UpdateRaceRequest INVALID_NAME_BLANK_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name(" ")
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final UpdateRaceRequest INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name("")
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final UpdateRaceRequest INVALID_NAME_NULL_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name(null)
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    // Species ID
    public static final UpdateRaceRequest INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name(VALID_UPDATE_RACE_REQUEST.getName())
            .speciesId(10)
            .build();

    public static final UpdateRaceRequest INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name(VALID_UPDATE_RACE_REQUEST.getName())
            .speciesId(-1)
            .build();

    public static final UpdateRaceRequest INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST = UpdateRaceRequest
            .builder()
            .id(VALID_UPDATE_RACE_REQUEST.getId())
            .name(VALID_UPDATE_RACE_REQUEST.getName())
            .speciesId(null)
            .build();
}
