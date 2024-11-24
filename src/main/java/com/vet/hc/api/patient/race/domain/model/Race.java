package com.vet.hc.api.patient.race.domain.model;

import com.vet.hc.api.patient.species.domain.model.Species;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a race.
 *
 * <p>
 * The races is a group of patients of different races.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    private Long id;

    private String name;
    private Species species;
}
