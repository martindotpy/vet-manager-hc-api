package com.vet.hc.api.appointment.core.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.appointment.core.adapter.in.request.UpdateAppointmentRequest;
import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.core.domain.payload.UpdateAppointmentPayload;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

public class UpdateAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private UpdateAppointmentUseCase updateAppointmentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateSuccess() {
        UpdateAppointmentPayload payload = UpdateAppointmentRequest.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .description("Updated description")
                .build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .description("Old description")
                .patient(Patient.builder().id(1L).build())
                .build();

        Appointment updatedAppointment = Appointment.builder()
                .id(1L)
                .startAt(payload.getStartAt())
                .description(payload.getDescription())
                .patient(Patient.builder().id(1L).build())
                .build();

        AppointmentDto appointmentDto = new AppointmentDto();

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(Result.success(updatedAppointment));
        when(appointmentMapper.toDto(any(Appointment.class))).thenReturn(appointmentDto);

        Result<AppointmentDto, AppointmentFailure> result = updateAppointmentUseCase.update(payload);

        assertEquals(Result.success(appointmentDto), result);
    }

    @Test
    void testUpdateAppointmentNotFound() {
        UpdateAppointmentPayload payload = UpdateAppointmentRequest.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .description("Updated description")
                .build();

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Result<AppointmentDto, AppointmentFailure> result = updateAppointmentUseCase.update(payload);

        assertEquals(Result.failure(AppointmentFailure.NOT_FOUND), result);
    }

    @Test
    void testUpdateUnexpectedError() {
        UpdateAppointmentPayload payload = UpdateAppointmentRequest.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .description("Updated description")
                .build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .description("Old description")
                .patient(Patient.builder().id(1L).build())
                .build();

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(Result.failure(RepositoryFailure.UNEXPECTED));

        Result<AppointmentDto, AppointmentFailure> result = updateAppointmentUseCase.update(payload);

        assertEquals(Result.failure(AppointmentFailure.UNEXPECTED), result);
    }
}