package com.vet.hc.api.patient.species.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a species.
 *
 * <p>
 * The species is a group of patients of different races.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Species {
    private Long id;

    private String name;
}
