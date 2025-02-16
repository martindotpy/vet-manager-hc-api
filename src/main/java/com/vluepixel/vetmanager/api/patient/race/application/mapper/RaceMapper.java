package com.vluepixel.vetmanager.api.patient.race.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.patient.race.domain.model.Race;
import com.vluepixel.vetmanager.api.patient.race.domain.request.CreateRaceRequest;
import com.vluepixel.vetmanager.api.patient.race.domain.request.UpdateRaceRequest;
import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Race mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface RaceMapper
        extends CrudMapper<Race, RaceDto, Race.RaceBuilder> {
    /**
     * Creates a new {@link Race} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default Race.RaceBuilder createRaceBuilder() {
        return Race.builder();
    }

    /**
     * Create race from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>id</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the create race request.
     * @return the race builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "species", source = "speciesId")
    Race.RaceBuilder fromRequest(CreateRaceRequest request);

    /**
     * Update race from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>id</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the update race request.
     * @return the race builder
     */
    @Mapping(target = "species", source = "speciesId")
    Race.RaceBuilder fromRequest(UpdateRaceRequest request);

    /**
     * Maps species id to species.
     *
     * @param speciesId the species id.
     * @return the species
     */
    default Species mapSpeciesIdToSpecies(Integer speciesId) {
        return Species.builder().id(speciesId).build();
    }
}
