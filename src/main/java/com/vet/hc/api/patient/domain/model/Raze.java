package com.vet.hc.api.patient.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents an animal raze.
 */
@Getter
@Builder
public class Raze {
    private Long id;

    private String name;

    private Specie specie;
}
