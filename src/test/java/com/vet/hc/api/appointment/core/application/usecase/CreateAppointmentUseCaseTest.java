package com.vet.hc.api.appointment.core.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.core.domain.payload.CreateAppointmentPayload;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.auth.core.application.port.in.GetAuthenticatedUserPort;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.dto.UserDto;

public class CreateAppointmentUseCaseTest {

    @Mock
    private GetAuthenticatedUserPort getAuthenticatedUserPort;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private CreateAppointmentUseCase createAppointmentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAppointmentSuccess() {
        CreateAppointmentPayload payload = Mockito.mock(CreateAppointmentPayload.class);
        Appointment appointment = Mockito.mock(Appointment.class);
        AppointmentDto appointmentDto = Mockito.mock(AppointmentDto.class);
        UserDto userDto = Mockito.mock(UserDto.class);

        when(payload.getPatientId()).thenReturn(1L);
        when(payload.getStartAt()).thenReturn(LocalDateTime.now());
        when(payload.getDescription()).thenReturn("Description");
        when(getAuthenticatedUserPort.get()).thenReturn(Optional.of(userDto));
        when(userDto.getId()).thenReturn(1L);
        when(appointmentRepository.save(any())).thenReturn(Result.success(appointment));
        when(appointmentMapper.toDto(any())).thenReturn(appointmentDto);

        Result<AppointmentDto, AppointmentFailure> result = createAppointmentUseCase.create(payload);

        assertEquals(Result.success(appointmentDto), result);
    }

    @Test
    void testCreateAppointmentFailurePatientNotFound() {
        CreateAppointmentPayload payload = Mockito.mock(CreateAppointmentPayload.class);
        when(payload.getPatientId()).thenReturn(1L);
        when(payload.getStartAt()).thenReturn(LocalDateTime.now());
        when(payload.getDescription()).thenReturn("Description");
        when(getAuthenticatedUserPort.get()).thenReturn(Optional.of(Mockito.mock(UserDto.class)));
        RepositoryFailure failure = RepositoryFailure.ENTITY_NOT_FOUND;
        failure.setField("patient");
        when(appointmentRepository.save(any()))
                .thenReturn(Result.failure(failure));

        Result<AppointmentDto, AppointmentFailure> result = createAppointmentUseCase.create(payload);

        assertEquals(Result.failure(AppointmentFailure.PATIENT_NOT_FOUND), result);
    }

    @Test
    void testCreateAppointmentFailureUnexpected() {
        CreateAppointmentPayload payload = Mockito.mock(CreateAppointmentPayload.class);

        when(payload.getPatientId()).thenReturn(1L);
        when(payload.getStartAt()).thenReturn(LocalDateTime.now());
        when(payload.getDescription()).thenReturn("Description");
        when(getAuthenticatedUserPort.get()).thenReturn(Optional.of(Mockito.mock(UserDto.class)));
        when(appointmentRepository.save(any())).thenReturn(Result.failure(RepositoryFailure.UNEXPECTED));

        Result<AppointmentDto, AppointmentFailure> result = createAppointmentUseCase.create(payload);

        assertEquals(Result.failure(AppointmentFailure.UNEXPECTED), result);
    }
}