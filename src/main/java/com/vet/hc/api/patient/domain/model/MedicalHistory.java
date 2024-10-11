package com.vet.hc.api.patient.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a medical history.
 */
@Getter
@Builder
public class MedicalHistory {
    private Long id;

    private String description;

    private Patient patient;
}
