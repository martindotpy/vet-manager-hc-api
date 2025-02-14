package com.vluepixel.vetmanager.api.patient.species.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;
import com.vluepixel.vetmanager.api.patient.species.domain.request.CreateSpeciesRequest;
import com.vluepixel.vetmanager.api.patient.species.domain.request.UpdateSpeciesRequest;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Species mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface SpeciesMapper
        extends CrudMapper<Species, SpeciesDto, Species.SpeciesBuilder> {
    @ObjectFactory
    default Species.SpeciesBuilder createSpeciesBuilder() {
        return Species.builder();
    }

    /**
     * Create species from request.
     *
     * @param request the create species request.
     * @return the species builder
     */
    @Mapping(target = "id", ignore = true)
    Species.SpeciesBuilder fromRequest(CreateSpeciesRequest request);

    /**
     * Update species from request.
     *
     * @param request the update species request.
     * @return the species builder
     */
    Species.SpeciesBuilder fromRequest(UpdateSpeciesRequest request);
}
