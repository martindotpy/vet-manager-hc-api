package com.vet.hc.api.patient.core.domain.payload;

import java.time.LocalDate;

import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update patient payload.
 */
public interface UpdatePatientPayload extends Payload {
    Long getId();

    String getName();

    LocalDate getBirthDate();

    String getCharacteristics();

    Genre getGenre();

    Long getOwnerId();

    Long getRaceId();

    // List<UpdateMedicalHistoryPayload> getMedicalHistories();
}
