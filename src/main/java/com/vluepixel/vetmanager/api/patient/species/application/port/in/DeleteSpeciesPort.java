package com.vluepixel.vetmanager.api.patient.species.application.port.in;

/**
 * Delete species port.
 */
public interface DeleteSpeciesPort {
    /**
     * Delete species by id.
     *
     * @param id the species id.
     */
    void deleteById(Integer id);
}
