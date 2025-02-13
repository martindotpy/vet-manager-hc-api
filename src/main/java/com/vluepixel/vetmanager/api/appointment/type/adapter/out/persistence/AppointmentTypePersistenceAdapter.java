package com.vluepixel.vetmanager.api.appointment.type.adapter.out.persistence;

import com.vluepixel.vetmanager.api.appointment.type.adapter.out.persistence.repository.AppointmentTypeSpringRepository;
import com.vluepixel.vetmanager.api.appointment.type.domain.model.AppointmentType;
import com.vluepixel.vetmanager.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link AppointmentType}.
 */
@PersistenceAdapter
public class AppointmentTypePersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<AppointmentType, Long, AppointmentTypeSpringRepository>
        implements AppointmentTypeRepository {
    public AppointmentTypePersistenceAdapter(AppointmentTypeSpringRepository repository) {
        super(repository);
    }
}
