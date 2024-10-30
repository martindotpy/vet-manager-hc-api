package com.vet.hc.api.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.auth.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.domain.command.LoginUserCommand;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.application.response.UserDto;
import com.vet.hc.api.user.domain.model.User;
import com.vet.hc.api.user.domain.repository.UserRepository;

public class LoginUserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUserService loginUserService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginUserSuccess() {
        LoginUserCommand command = new LoginUserCommand("john.doe@example.com", "password123");
        User user = User.builder()
                .email(command.getEmail())
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail(command.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(command.getPassword(), user.getPassword())).thenReturn(true);

        Result<UserDto, AuthFailure> result = loginUserService.login(command);

        assertTrue(result.isSuccess());
        assertEquals("john.doe@example.com", result.getSuccess().getEmail());
    }

    @Test
    public void testLoginUserInvalidCredentials() {
        LoginUserCommand command = new LoginUserCommand("john.doe@example.com", "wrongPassword");
        User user = User.builder()
                .email(command.getEmail())
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail(command.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(command.getPassword(), user.getPassword())).thenReturn(false);

        Result<UserDto, AuthFailure> result = loginUserService.login(command);

        assertTrue(result.isFailure());
        assertEquals(AuthFailure.INVALID_CREDENTIALS, result.getFailure());
    }

    @Test
    public void testLoginUserNotFound() {
        LoginUserCommand command = new LoginUserCommand("john.doe@example.com", "password123");

        when(userRepository.findByEmail(command.getEmail())).thenReturn(Optional.empty());

        Result<UserDto, AuthFailure> result = loginUserService.login(command);

        assertTrue(result.isFailure());
        assertEquals(AuthFailure.INVALID_CREDENTIALS, result.getFailure());
    }
}