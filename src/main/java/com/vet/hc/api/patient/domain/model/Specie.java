package com.vet.hc.api.patient.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents an animal specie.
 */
@Getter
@Builder
public class Specie {
    private Long id;

    private String name;
}
