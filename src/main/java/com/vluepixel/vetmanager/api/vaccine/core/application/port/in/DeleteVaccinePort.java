package com.vluepixel.vetmanager.api.vaccine.core.application.port.in;

/**
 * Delete vaccine port.
 */
public interface DeleteVaccinePort {
    /**
     * Delete vaccine by patient id and id.
     *
     * @param patientId the patient id.
     * @param id        the vaccine id.
     */
    void deleteByPatientIdAndId(Long patientId, Long id);
}
