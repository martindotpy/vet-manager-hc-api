package com.vluepixel.vetmanager.api.patient.race.application.port.in;

/**
 * Delete race port.
 */
public interface DeleteRacePort {
    /**
     * Delete race by id.
     *
     * @param id the race id.
     */
    void deleteById(Integer id);
}
