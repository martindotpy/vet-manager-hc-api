package com.vet.hc.api.auth.adapter.in.controller;

import com.vet.hc.api.auth.adapter.in.mapper.LoginUserMapper;
import com.vet.hc.api.auth.adapter.in.mapper.RegisterUserMapper;
import com.vet.hc.api.auth.adapter.in.request.LoginUserRequest;
import com.vet.hc.api.auth.adapter.in.request.RegisterUserRequest;
import com.vet.hc.api.auth.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.domain.command.LoginUserCommand;
import com.vet.hc.api.auth.domain.command.RegisterUserCommand;
import com.vet.hc.api.auth.domain.failure.EmailAlreadyInUseFailure;
import com.vet.hc.api.auth.domain.failure.InvalidCredentials;
import com.vet.hc.api.shared.domain.query.FailureResponse;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.application.response.UserDto;
import com.vet.hc.api.user.domain.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

/**
 * Controller for authentication.
 */
@Tag(name = "Authentication endpoints", description = "Endpoints for authentication")
@Path("/auth")
@NoArgsConstructor
@ApplicationScoped
public class AuthController {
    private RegisterUserPort registerUserPort;
    private LoginUserPort loginUserPort;

    private RegisterUserMapper registerMapper = RegisterUserMapper.INSTANCE;
    private LoginUserMapper loginMapper = LoginUserMapper.INSTANCE;

    private Validator validator;

    @Inject
    public AuthController(LoginUserPort loginUserPort, RegisterUserPort registerUserPort, Validator validator) {
        this.loginUserPort = loginUserPort;
        this.registerUserPort = registerUserPort;
        this.validator = validator;
    }

    /**
     * Login a user.
     *
     * @return the user
     */
    @Operation(summary = "Login the user", responses = {
            @ApiResponse(responseCode = "200", description = "User logged successfully", content = @Content(schema = @Schema(implementation = LoginUserRequest.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginUserRequest loginRequest) {
        var violations = validator.validate(loginRequest);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        LoginUserCommand command = loginMapper.toCommand(loginRequest);
        Result<UserDto, InvalidCredentials> result = loginUserPort.login(command);

        if (result.isFailure()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(FailureResponse.builder()
                            .message("Credenciales inválidas")
                            .build())
                    .build();
        }

        return Response.ok(result.getSuccess()).build();
    }

    /**
     * Register a new user.
     *
     * @param request the register request.
     * @return the user registered
     */
    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = RegisterUserRequest.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterUserRequest request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        RegisterUserCommand command = registerMapper.toCommand(request);
        Result<UserDto, EmailAlreadyInUseFailure> result = registerUserPort.register(command);

        if (result.isFailure()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(FailureResponse.builder()
                            .message("Correo ya en uso")
                            .build())
                    .build();
        }

        return Response.ok(result.getSuccess()).build();
    }

    /**
     * Get the current user.
     *
     * @return the current user
     */
    @Path("/me")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Object me() {

        return new User();
    }
}
