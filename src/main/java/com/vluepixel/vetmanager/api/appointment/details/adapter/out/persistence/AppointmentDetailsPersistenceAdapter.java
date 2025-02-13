package com.vluepixel.vetmanager.api.appointment.details.adapter.out.persistence;

import com.vluepixel.vetmanager.api.appointment.details.adapter.out.persistence.repository.AppointmentDetailsSpringRepository;
import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link AppointmentDetails}.
 */
@PersistenceAdapter
public class AppointmentDetailsPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<AppointmentDetails, Long, AppointmentDetailsSpringRepository>
        implements AppointmentDetailsRepository {
    public AppointmentDetailsPersistenceAdapter(AppointmentDetailsSpringRepository repository) {
        super(repository);
    }
}
