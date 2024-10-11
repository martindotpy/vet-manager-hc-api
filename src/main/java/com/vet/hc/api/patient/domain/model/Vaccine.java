package com.vet.hc.api.patient.domain.model;

import java.time.LocalDateTime;

import com.vet.hc.api.sales.domain.model.Sale;
import com.vet.hc.api.vet.domain.model.Vet;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a vaccine.
 */
@Getter
@Builder
public class Vaccine {
    private Long id;

    private String name;
    private Integer dose;
    private LocalDateTime providedAt;

    private Patient patient;
    private Vet vaccinatedBy;
    private Sale sale;
}
