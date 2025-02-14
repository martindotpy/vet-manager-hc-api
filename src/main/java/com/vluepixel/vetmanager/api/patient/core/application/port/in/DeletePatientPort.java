package com.vluepixel.vetmanager.api.patient.core.application.port.in;

/**
 * Delete patient port.
 */
public interface DeletePatientPort {
    /**
     * Delete patient by id.
     *
     * @param id the patient id
     */
    void deleteById(Long id);
}
