package com.vet.hc.api.appointment.core.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.details.application.port.in.CreateAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.payload.CreateAppointmentDetailsPayload;
import com.vet.hc.api.shared.domain.query.Result;

public class AddDetailsToAppointmentUseCaseTest {
    @Mock
    private FindAppointmentPort findAppointmentPort;

    @Mock
    private CreateAppointmentDetailsPort createAppointmentDetailsPort;

    @InjectMocks
    private AddDetailsToAppointmentUseCase addDetailsToAppointmentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDetailsToAppointmentSuccess() {
        CreateAppointmentDetailsPayload payload = Mockito.mock(CreateAppointmentDetailsPayload.class);
        AppointmentDto appointmentDto = Mockito.mock(AppointmentDto.class);

        when(createAppointmentDetailsPort.create(any())).thenReturn(Result.success());
        when(findAppointmentPort.findById(any())).thenReturn(Result.success(appointmentDto));

        Result<AppointmentDto, AppointmentFailure> result = addDetailsToAppointmentUseCase.add(payload);

        assertEquals(Result.success(appointmentDto), result);
    }

    @Test
    void testAddDetailsToAppointmentFailureOnCreate() {
        CreateAppointmentDetailsPayload payload = Mockito.mock(CreateAppointmentDetailsPayload.class);

        when(createAppointmentDetailsPort.create(any()))
                .thenReturn(Result.failure(AppointmentDetailsFailure.UNEXPECTED));

        Result<AppointmentDto, AppointmentFailure> result = addDetailsToAppointmentUseCase.add(payload);

        assertEquals(Result.failure(AppointmentFailure.UNEXPECTED), result);
    }

    @Test
    void testAddDetailsToAppointmentFailureOnFind() {
        CreateAppointmentDetailsPayload payload = Mockito.mock(CreateAppointmentDetailsPayload.class);

        when(createAppointmentDetailsPort.create(any())).thenReturn(Result.success());
        when(findAppointmentPort.findById(any())).thenReturn(Result.failure(AppointmentFailure.NOT_FOUND));

        Result<AppointmentDto, AppointmentFailure> result = addDetailsToAppointmentUseCase.add(payload);

        assertEquals(Result.failure(AppointmentFailure.NOT_FOUND), result);
    }
}