package com.vet.hc.api.auth.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toValidationFailureResponse;

import com.vet.hc.api.auth.core.adapter.in.request.LoginUserDto;
import com.vet.hc.api.auth.core.adapter.in.request.RegisterUserDto;
import com.vet.hc.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vet.hc.api.auth.core.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.core.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.core.application.port.out.JwtAuthenticationPort;
import com.vet.hc.api.auth.core.domain.dto.JwtDto;
import com.vet.hc.api.auth.core.domain.failure.AuthFailure;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.query.ValidationErrorResponse;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for authentication.
 */
@Slf4j
@Tag(name = "Authentication", description = "Application authentication")
@Path("/auth")
@NoArgsConstructor
public class AuthController {
    private RegisterUserPort registerUserPort;
    private LoginUserPort loginUserPort;
    private JwtAuthenticationPort jwtAuthenticationPort;

    @Inject
    public AuthController(
            LoginUserPort loginUserPort,
            RegisterUserPort registerUserPort,
            JwtAuthenticationPort jwtAuthenticationPort) {
        this.loginUserPort = loginUserPort;
        this.registerUserPort = registerUserPort;
        this.jwtAuthenticationPort = jwtAuthenticationPort;
    }

    /**
     * Login a user.
     *
     * @return the user
     */
    @Operation(summary = "Login the user", description = "Login the user with the given credentials.", responses = {
            @ApiResponse(responseCode = "200", description = "User logged successfully.", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginUserDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toValidationFailureResponse(validationErrors);

        Result<UserDto, AuthFailure> result = loginUserPort.login(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        String jwt = jwtAuthenticationPort.generateJwt(result.getSuccess());

        log.info("Sending JWT to the user with email: {}", request.getEmail());

        return Response.ok(
                AuthenticationResponse.builder()
                        .message("Usuario logueado exitosamente")
                        .content(JwtDto.builder()
                                .jwt(jwt)
                                .build())
                        .build())
                .build();
    }

    /**
     * Register a new user.
     *
     * @param request the register request.
     * @return the user registered
     */
    @Operation(summary = "Register a new user", description = "Register a new user. Only an admin user can register other users. The new user must have at least one role.", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully.", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already exists.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterUserDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toValidationFailureResponse(validationErrors);

        Result<UserDto, AuthFailure> result = registerUserPort.register(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        String jwt = jwtAuthenticationPort.generateJwt(result.getSuccess());

        log.info("Sending JWT to the user with email: {}", request.getEmail());

        return Response.ok(
                AuthenticationResponse.builder()
                        .message("Usuario registrado exitosamente")
                        .content(JwtDto.builder()
                                .jwt(jwt)
                                .build())
                        .build())
                .build();
    }
}
