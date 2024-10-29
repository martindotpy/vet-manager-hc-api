package com.vet.hc.api.patient.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a medical history.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistory {
    private Long id;

    private String description;

    private Patient patient;
}
