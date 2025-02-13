package com.vluepixel.vetmanager.api.appointment.details.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.CreateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.UpdateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.appointment.type.domain.model.AppointmentType;
import com.vluepixel.vetmanager.api.shared.application.mapper.CrudMapper;
import com.vluepixel.vetmanager.api.shared.application.mapper.StringUtilsMapper;

/**
 * Appointment details mapper.
 */
@Mapper(componentModel = "spring", uses = { StringUtilsMapper.class })
public interface AppointmentDetailsMapper
        extends CrudMapper<AppointmentDetails, AppointmentDetailsDto, AppointmentDetails.AppointmentDetailsBuilder> {
    AppointmentDetailsMapper INSTANCE = Mappers.getMapper(AppointmentDetailsMapper.class);

    @ObjectFactory
    default AppointmentDetails.AppointmentDetailsBuilder createAppointmentDetailsBuilder() {
        return AppointmentDetails.builder();
    }

    /**
     * Create appointment details from request.
     *
     * @param request the create appointment details request.
     * @return the appointment details builder
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", source = "appointmentTypeId")
    AppointmentDetails.AppointmentDetailsBuilder fromRequest(CreateAppointmentDetailsRequest request);

    /**
     * Update appointment details from request.
     *
     * @param request the update appointment details request.
     * @return the appointment details builder
     */
    @Mapping(target = "type", source = "appointmentTypeId")
    AppointmentDetails.AppointmentDetailsBuilder fromRequest(UpdateAppointmentDetailsRequest request);

    default AppointmentType mapAppointmentTypeIdToType(Long appointmentTypeId) {
        return AppointmentType.builder().id(appointmentTypeId).build();
    }
}
