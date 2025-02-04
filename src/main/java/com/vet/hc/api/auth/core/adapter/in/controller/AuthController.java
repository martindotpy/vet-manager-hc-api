package com.vet.hc.api.auth.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.respondContentResult;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserRequest;
import com.vet.hc.api.auth.core.adapter.in.request.RegisterUserRequest;
import com.vet.hc.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vet.hc.api.auth.core.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.core.application.port.in.RegisterUserPort;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.annotations.RestControllerAdapter;
import com.vet.hc.api.shared.domain.validation.ValidationPayload;
import com.vet.hc.api.user.core.adapter.in.response.UserResponse;

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
    private final RegisterUserPort registerUserPort;
    private final LoginUserPort loginUserPort;

    /**
     * Login a user.
     *
     * @param request the login request.
     * @return the user
     */
    @Operation(summary = "Login the user", description = "Login the user with the given credentials", responses = {
            @ApiResponse(responseCode = "200", description = "User logged successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already authenticated", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request) {
        return respondContentResult(
                AuthenticationResponse.class,
                () -> loginUserPort.login(request),
                "Usuario " + request.getEmail() + " ha ingresado correctamente",
                ValidationPayload.of(request));
    }

    /**
     * Register a new user.
     *
     * @param request the register request.
     * @return the user registered
     */
    @Operation(summary = "Register a new user", description = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        return respondContentResult(
                UserResponse.class,
                () -> registerUserPort.register(request),
                "Usuario " + request.getEmail() + " ha sido registrado correctamente",
                ValidationPayload.of(request));
    }
}
