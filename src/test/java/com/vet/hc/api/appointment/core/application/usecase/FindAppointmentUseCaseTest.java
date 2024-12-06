package com.vet.hc.api.appointment.core.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.shared.domain.query.Result;

public class FindAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private FindAppointmentUseCase findAppointmentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdSuccess() {
        Long id = 1L;
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(new Appointment()));
        when(appointmentMapper.toDto(any())).thenReturn(appointmentDto);

        Result<AppointmentDto, AppointmentFailure> result = findAppointmentUseCase.findById(id);

        assertEquals(Result.success(appointmentDto), result);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 1L;
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Result<AppointmentDto, AppointmentFailure> result = findAppointmentUseCase.findById(id);

        assertEquals(Result.failure(AppointmentFailure.NOT_FOUND), result);
    }
}