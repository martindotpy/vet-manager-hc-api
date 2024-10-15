package com.vet.hc.api.patient.domain.model;

import java.time.LocalDate;

import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.patient.domain.enums.Genre;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a patient.
 *
 * <p>
 * The patient is the animal that is being treated by the veterinarian. In other
 * words, it is the pet of the client.
 * </p>
 */
@Getter
@Builder
public class Patient {
    private Long id;

    private String name;
    private LocalDate birthDay;
    private Byte age;
    private Genre genre;
    private String characteristics;
    private boolean isDeceased;

    private Client owner;
    private Raze raze;
}
