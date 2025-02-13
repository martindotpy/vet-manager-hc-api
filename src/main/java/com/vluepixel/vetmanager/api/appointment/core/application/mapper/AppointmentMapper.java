package com.vluepixel.vetmanager.api.appointment.core.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.appointment.core.domain.model.Appointment;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.CreateAppointmentRequest;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.UpdateAppointmentRequest;
import com.vluepixel.vetmanager.api.appointment.details.application.mapper.AppointmentDetailsMapper;
import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.CreateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.UpdateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Appointment mapper.
 */
@Mapper(componentModel = "spring", uses = { AppointmentDetailsMapper.class, StringUtilsMapper.class })
public interface AppointmentMapper
        extends CrudMapper<Appointment, AppointmentDto, Appointment.AppointmentBuilder> {
    @ObjectFactory
    default Appointment.AppointmentBuilder createAppointmentBuilder() {
        return Appointment.builder();
    }

    /**
     * Create appointment from request.
     *
     * @param request the create appointment request.
     * @return the appointment builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Appointment.AppointmentBuilder fromRequest(CreateAppointmentRequest request);

    /**
     * Update appointment from request.
     *
     * @param request the update appointment request.
     * @return the appointment builder
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Appointment.AppointmentBuilder fromRequest(UpdateAppointmentRequest request);

    default AppointmentDetails mapDetailsToDetails(CreateAppointmentDetailsRequest request) {
        return AppointmentDetailsMapper.INSTANCE.fromRequest(request).build();
    }

    default AppointmentDetails mapDetailsToDetails(UpdateAppointmentDetailsRequest request) {
        return AppointmentDetailsMapper.INSTANCE.fromRequest(request).build();
    }
}
