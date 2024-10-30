package com.vet.hc.api.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.auth.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.domain.command.RegisterUserCommand;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.application.response.UserDto;
import com.vet.hc.api.user.domain.enums.UserRole;
import com.vet.hc.api.user.domain.model.User;
import com.vet.hc.api.user.domain.repository.UserRepository;

public class RegisterUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserService registerUserService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUserSuccess() {
        RegisterUserCommand command = new RegisterUserCommand("John", "Doe", "john.doe@example.com", "password123",
                Set.of(UserRole.USER));
        User user = User.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .password("encodedPassword")
                .roles(command.getRoles())
                .build();
        Result<User, RepositoryFailure> userResult = Result.success(user);

        when(passwordEncoder.encode(command.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userResult);

        Result<UserDto, AuthFailure> result = registerUserService.register(command);

        verify(passwordEncoder).encode(command.getPassword());
        verify(userRepository).save(any(User.class));

        assertTrue(result.isSuccess());
        assertEquals("john.doe@example.com", result.getSuccess().getEmail());
    }

    @Test
    public void testRegisterUserDuplicateEmail() {
        RegisterUserCommand command = new RegisterUserCommand("John", "Doe", "john.doe@example.com", "password123",
                Set.of(UserRole.USER));
        Result<User, RepositoryFailure> userResult = Result.failure(RepositoryFailure.DUPLICATE);

        when(passwordEncoder.encode(command.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userResult);

        Result<UserDto, AuthFailure> result = registerUserService.register(command);

        verify(passwordEncoder).encode(command.getPassword());
        verify(userRepository).save(any(User.class));

        assertTrue(result.isFailure());
        assertEquals(AuthFailure.EMAIL_ALREADY_IN_USE, result.getFailure());
    }

    @Test
    public void testRegisterUserUnexpectedFailure() {
        RegisterUserCommand command = new RegisterUserCommand("John", "Doe", "john.doe@example.com", "password123",
                Set.of(UserRole.USER));
        Result<User, RepositoryFailure> userResult = Result.failure(RepositoryFailure.UNEXPECTED);

        when(passwordEncoder.encode(command.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userResult);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registerUserService.register(command);
        });

        verify(passwordEncoder).encode(command.getPassword());
        verify(userRepository).save(any(User.class));

        assertEquals("Unexpected repository failure: UNEXPECTED", exception.getMessage());
    }
}
