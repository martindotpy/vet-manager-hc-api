package com.vet.hc.api.patient.domain.model;

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
