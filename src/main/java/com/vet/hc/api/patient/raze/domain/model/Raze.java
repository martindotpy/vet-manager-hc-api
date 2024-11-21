package com.vet.hc.api.patient.raze.domain.model;

import com.vet.hc.api.patient.specie.domain.model.Specie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an animal raze.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Raze {
    private Long id;

    private String name;

    private Specie specie;
}
