package com.vet.hc.api.auth.adapter.in.controller;

import com.vet.hc.api.auth.adapter.in.mapper.RegisterMapper;
import com.vet.hc.api.auth.adapter.in.request.RegisterRequest;
import com.vet.hc.api.auth.application.port.in.RegisterUserPort;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Controller for authentication.
 */
@Tag(name = "Authentication endpoints", description = "Endpoints for authentication")
@Path("/auth")
@ApplicationScoped
public class AuthController {
    private RegisterUserPort registerUserPort;
    private RegisterMapper registerMapper = RegisterMapper.INSTANCE;

    public AuthController() {
    }

    @Inject
    public AuthController(RegisterUserPort registerUserPort) {
        this.registerUserPort = registerUserPort;
    }

    /**
     * Login a user.
     *
     * @return the user
     */
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Object login() {

        return new User();
    }

    /**
     * Register a new user.
     *
     * @param request the register request.
     * @return the user registered
     */
    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = RegisterRequest.class))),
    })
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object register(RegisterRequest request) {
        System.out.println(request);
        System.out.println(registerMapper.toCommand(request));
        Result<UserDto, AuthFailure> result = registerUserPort.register(registerMapper.toCommand(request));

        if (result.isFailure()) {
            return result.getError();
        }

        return result.getSuccess();
    }

    /**
     * Logout a user.
     */
    @Path("/logout")
    @POST
    public void logout() {

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
