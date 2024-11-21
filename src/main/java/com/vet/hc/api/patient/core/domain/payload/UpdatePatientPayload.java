package com.vet.hc.api.patient.core.domain.payload;

import java.time.LocalDate;
import java.util.List;

import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.medicalhistory.domain.payload.UpdateMedicalHistoryPayload;
import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to update a patient.
 */
public interface UpdatePatientPayload extends Payload {
    Long getId();

    String getName();

    LocalDate getBirthDate();

    String getCharacteristics();

    Genre getGenre();

    Long getOwnerId();

    Long getRazeId();

    List<UpdateMedicalHistoryPayload> getMedicalHistories();
}
