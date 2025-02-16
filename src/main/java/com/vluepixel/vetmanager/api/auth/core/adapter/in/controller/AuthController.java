package com.vluepixel.vetmanager.api.auth.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vluepixel.vetmanager.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vluepixel.vetmanager.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vluepixel.vetmanager.api.auth.core.application.port.in.LoginUserPort;
import com.vluepixel.vetmanager.api.auth.core.application.port.in.RegisterUserPort;
import com.vluepixel.vetmanager.api.auth.core.application.port.in.UpdatePasswordPort;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.auth.core.domain.exception.InvalidCredentialsException;
import com.vluepixel.vetmanager.api.auth.core.domain.exception.UserAlreadyAuthenticatedException;
import com.vluepixel.vetmanager.api.auth.core.domain.request.LoginUserRequest;
import com.vluepixel.vetmanager.api.auth.core.domain.request.RegisterUserRequest;
import com.vluepixel.vetmanager.api.auth.core.domain.request.UpdatePasswordRequest;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.FailureResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.user.core.adapter.in.response.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Authentication controller.
 */
@Tag(name = "Authentication", description = "Application authentication")
@RestControllerAdapter
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final GetCurrentUserPort getCurrentUserPort;

    private final RegisterUserPort registerUserPort;
    private final LoginUserPort loginUserPort;
    private final UpdatePasswordPort updatePasswordPort;

    /**
     * Login a user.
     *
     * @param request the login request.
     * @return Response with the JWT token
     */
    @Operation(summary = "Login the user", description = "Login the user with the given credentials", responses = {
            @ApiResponse(responseCode = "200", description = "User logged successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already authenticated", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginUserRequest request) {
        try {
            getCurrentUserPort.get();

            throw new UserAlreadyAuthenticatedException();
        } catch (GetUserWhenDoNotLoggedInException ignored) {
        }

        return ok(() -> loginUserPort.login(request),
                "Usuario " + request.getEmail() + " ha ingresado correctamente",
                ValidationRequest.of(request));
    }

    /**
     * Register a new user.
     *
     * @param request the register request.
     * @return Response with the registered user
     */
    @Operation(summary = "Register a new user", description = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest request) {
        return ok(() -> registerUserPort.register(request),
                "Usuario " + request.getEmail() + " ha sido registrado correctamente",
                ValidationRequest.of(request));
    }

    /**
     * Update the password of the current user.
     *
     * @param request the update password request.
     * @return Response with an ok message
     */
    @Operation(summary = "Update the password", description = "Update the password of the current user", responses = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PutMapping("/password")
    public ResponseEntity<BasicResponse> updatePassword(@RequestBody UpdatePasswordRequest request)
            throws InvalidCredentialsException {
        return ok(() -> updatePasswordPort.update(request),
                "Contraseña actualizada correctamente",
                ValidationRequest.of(request));
    }
}
