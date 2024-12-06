package com.vet.hc.api.appointment.core.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vet.hc.api.appointment.details.application.port.in.CreateAppointmentDetailsPort;

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

}