package com.vluepixel.vetmanager.api.appointment.type.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.appointment.type.domain.model.AppointmentType;
import com.vluepixel.vetmanager.api.appointment.type.domain.request.CreateAppointmentTypeRequest;
import com.vluepixel.vetmanager.api.appointment.type.domain.request.UpdateAppointmentTypeRequest;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Appointment type mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface AppointmentTypeMapper
        extends CrudMapper<AppointmentType, AppointmentTypeDto, AppointmentType.AppointmentTypeBuilder> {
    /**
     * Creates a new {@link AppointmentType} builder.
     *
     * @return the builder
     */
    @ObjectFactory
    default AppointmentType.AppointmentTypeBuilder createAppointmentTypeBuilder() {
        return AppointmentType.builder();
    }

    /**
     * Create appointment type from request.
     *
     * <ul>
     * <li><strong>Ignores:</strong>
     * <ul>
     * <li><code>id</code></li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param request the create appointment type request.
     * @return the appointment type builder
     */
    @Mapping(target = "id", ignore = true)
    AppointmentType.AppointmentTypeBuilder fromRequest(CreateAppointmentTypeRequest request);

    /**
     * Update appointment type from request.
     *
     * @param request the update appointment type request.
     * @return the appointment type builder
     */
    AppointmentType.AppointmentTypeBuilder fromRequest(UpdateAppointmentTypeRequest request);
}
