package com.vet.hc.api.auth.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserRequest;
import com.vet.hc.api.auth.core.adapter.in.request.RegisterUserRequest;
import com.vet.hc.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vet.hc.api.auth.core.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.core.application.port.in.RegisterUserPort;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Authentication controller.
 */
@Slf4j
@Tag(name = "Authentication", description = "Application authentication")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUserPort registerUserPort;
    private final LoginUserPort loginUserPort;

    /**
     * Login a user.
     *
     * @return the user
     */
    @Operation(summary = "Login the user", description = "Login the user with the given credentials", responses = {
            @ApiResponse(responseCode = "200", description = "User logged successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "403", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request) {
        var violations = request.validate();

        if (!violations.isEmpty()) {
            return toDetailedFailureResponse(violations);
        }

        var result = loginUserPort.login(request);

        if (result.isFailure()) {
            return toFailureResponse(result.getFailure());
        }

        return toOkResponse(AuthenticationResponse.class, result.getSuccess(), "User logged in");
    }

    /**
     * Register a new user.
     *
     * @param request the register request.
     * @return the user registered
     */
    @Operation(summary = "Register a new user", description = "Register a new user. Only an admin user can register other users. The new user must have at least one role.", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully.", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already exists.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @SecurityRequirements
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        var violations = request.validate();

        if (!violations.isEmpty()) {
            return toDetailedFailureResponse(violations);
        }

        var result = registerUserPort.register(request);

        if (result.isFailure()) {
            return toFailureResponse(result.getFailure());
        }

        return toOkResponse(AuthenticationResponse.class, result.getSuccess(), "User registered");
    }
}
