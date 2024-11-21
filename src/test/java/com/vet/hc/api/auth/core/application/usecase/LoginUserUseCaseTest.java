package com.vet.hc.api.auth.core.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserDto;
import com.vet.hc.api.auth.core.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.core.application.usecase.data.LoginUserDataProvider;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

/**
 * Unit tests for {@link LoginUserUseCase}.
 */
public class LoginUserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUserUseCase loginUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUserSuccess() {
        LoginUserDto payload = LoginUserDataProvider.LOGIN_USER_DTO_CORRECT_PASSWORD;
        User user = User.builder()
                .email(payload.getEmail())
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail(payload.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(payload.getPassword(), user.getPassword())).thenReturn(true);

        Result<UserDto, AuthFailure> result = loginUserService.login(payload);

        assertTrue(result.isSuccess());
        assertEquals("john.doe@example.com", result.getSuccess().getEmail());
    }

    @Test
    void testLoginUserInvalidCredentials() {
        LoginUserDto payload = LoginUserDataProvider.LOGIN_USER_DTO_WRONG_PASSWORD;
        User user = User.builder()
                .email(payload.getEmail())
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail(payload.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(payload.getPassword(), user.getPassword())).thenReturn(false);

        Result<UserDto, AuthFailure> result = loginUserService.login(payload);

        assertTrue(result.isFailure());
        assertEquals(AuthFailure.INVALID_CREDENTIALS, result.getFailure());
    }

    @Test
    void testLoginUserNotFound() {
        LoginUserDto payload = LoginUserDataProvider.LOGIN_USER_DTO_CORRECT_PASSWORD;

        when(userRepository.findByEmail(payload.getEmail())).thenReturn(Optional.empty());

        Result<UserDto, AuthFailure> result = loginUserService.login(payload);

        assertTrue(result.isFailure());
        assertEquals(AuthFailure.INVALID_CREDENTIALS, result.getFailure());
    }
}