package com.vet.hc.api.auth.core.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vet.hc.api.auth.core.adapter.in.request.RegisterUserDto;
import com.vet.hc.api.auth.core.adapter.out.bean.PasswordEncoder;
import com.vet.hc.api.auth.core.application.usecase.data.RegisterUserDataProvider;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

/**
 * Unit tests for {@link RegisterUserUseCase}.
 */
public class RegisterUserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserUseCase registerUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        RegisterUserDto payload = RegisterUserDataProvider.REGISTER_USER_DTO;
        User user = User.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .password("encodedPassword")
                .roles(payload.getRoles())
                .build();
        Result<User, RepositoryFailure> userResult = Result.success(user);

        when(passwordEncoder.encode(payload.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userResult);

        Result<UserDto, AuthFailure> result = registerUserService.register(payload);

        verify(passwordEncoder).encode(payload.getPassword());
        verify(userRepository).save(any(User.class));

        assertTrue(result.isSuccess());
        assertEquals("john.doe@example.com", result.getSuccess().getEmail());
    }

    @Test
    void testRegisterUserDuplicateEmail() {
        RegisterUserDto payload = RegisterUserDataProvider.REGISTER_USER_DTO;
        Result<User, RepositoryFailure> userResult = Result.failure(RepositoryFailure.DUPLICATE);

        when(passwordEncoder.encode(payload.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userResult);

        Result<UserDto, AuthFailure> result = registerUserService.register(payload);

        verify(passwordEncoder).encode(payload.getPassword());
        verify(userRepository).save(any(User.class));

        assertTrue(result.isFailure());
        assertEquals(AuthFailure.EMAIL_ALREADY_IN_USE, result.getFailure());
    }

    @Test
    void testRegisterUserUnexpectedFailure() {
        RegisterUserDto payload = RegisterUserDataProvider.REGISTER_USER_DTO;
        Result<User, RepositoryFailure> userResult = Result.failure(RepositoryFailure.UNEXPECTED);

        when(passwordEncoder.encode(payload.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userResult);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registerUserService.register(payload);
        });

        verify(passwordEncoder).encode(payload.getPassword());
        verify(userRepository).save(any(User.class));

        assertEquals("Unexpected repository failure: UNEXPECTED", exception.getMessage());
    }
}
