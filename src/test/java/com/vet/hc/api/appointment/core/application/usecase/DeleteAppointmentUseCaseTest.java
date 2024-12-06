package com.vet.hc.api.appointment.core.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

public class DeleteAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private DeleteAppointmentUseCase deleteAppointmentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteAppointmentSuccess() {
        when(appointmentRepository.deleteById(anyLong())).thenReturn(Result.success());

        Result<Void, AppointmentFailure> result = deleteAppointmentUseCase.deleteById(1L);

        assertEquals(Result.success(), result);
    }

    @Test
    void testDeleteAppointmentNotFound() {
        when(appointmentRepository.deleteById(anyLong())).thenReturn(Result.failure(RepositoryFailure.NOT_FOUND));

        Result<Void, AppointmentFailure> result = deleteAppointmentUseCase.deleteById(1L);

        assertEquals(Result.failure(AppointmentFailure.NOT_FOUND), result);
    }

    @Test
    void testDeleteAppointmentUnexpectedError() {
        when(appointmentRepository.deleteById(anyLong())).thenReturn(Result.failure(RepositoryFailure.UNEXPECTED));

        Result<Void, AppointmentFailure> result = deleteAppointmentUseCase.deleteById(1L);

        assertEquals(Result.failure(AppointmentFailure.UNEXPECTED), result);
    }
}