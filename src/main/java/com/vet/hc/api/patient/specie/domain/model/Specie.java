package com.vet.hc.api.patient.specie.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an animal specie.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specie {
    private Long id;

    private String name;
}
