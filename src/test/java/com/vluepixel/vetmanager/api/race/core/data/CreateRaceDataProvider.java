package com.vluepixel.vetmanager.api.race.core.data;

import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_BLANK_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_NULL_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.VALID_UPDATE_RACE_REQUEST;

import com.vluepixel.vetmanager.api.patient.race.domain.request.CreateRaceRequest;

/**
 * Create race data provider.
 */
public class CreateRaceDataProvider {

    public static final CreateRaceRequest VALID_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(VALID_UPDATE_RACE_REQUEST.getName())
            .speciesId(VALID_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------

    // Name
    public static final CreateRaceRequest INVALID_NAME_TOO_LONG_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST.getName())
            .speciesId(INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final CreateRaceRequest VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getName())
            .speciesId(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final CreateRaceRequest INVALID_NAME_BLANK_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(INVALID_NAME_BLANK_UPDATE_RACE_REQUEST.getName())
            .speciesId(INVALID_NAME_BLANK_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final CreateRaceRequest INVALID_NAME_EMPTY_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST.getName())
            .speciesId(INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final CreateRaceRequest INVALID_NAME_NULL_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(INVALID_NAME_NULL_UPDATE_RACE_REQUEST.getName())
            .speciesId(INVALID_NAME_NULL_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    // Species ID
    public static final CreateRaceRequest INVALID_SPECIES_ID_NOT_FOUND_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST.getName())
            .speciesId(INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final CreateRaceRequest INVALID_SPECIES_ID_NEGATIVE_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST.getName())
            .speciesId(INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();

    public static final CreateRaceRequest INVALID_SPECIES_ID_NULL_CREATE_RACE_REQUEST = CreateRaceRequest
            .builder()
            .name(INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST.getName())
            .speciesId(INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST.getSpeciesId())
            .build();
}
