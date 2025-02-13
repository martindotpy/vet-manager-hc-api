package com.vluepixel.vetmanager.api.appointment.core.adapter.out.persistence;

import com.vluepixel.vetmanager.api.appointment.core.adapter.out.persistence.repository.AppointmentSpringRepository;
import com.vluepixel.vetmanager.api.appointment.core.domain.model.Appointment;
import com.vluepixel.vetmanager.api.appointment.core.domain.repository.AppointmentRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link Appointment}.
 */
@PersistenceAdapter
public class AppointmentPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<Appointment, Long, AppointmentSpringRepository>
        implements AppointmentRepository {
    public AppointmentPersistenceAdapter(AppointmentSpringRepository repository) {
        super(repository);
    }
}
