package com.vet.hc.api.client.adapter.in.controller;

import com.vet.hc.api.client.adapter.in.request.CreateClientRequest;
import com.vet.hc.api.client.adapter.in.request.UpdateClientEmailsRequest;
import com.vet.hc.api.client.adapter.in.request.UpdateClientPhonesRequest;
import com.vet.hc.api.client.adapter.in.response.FullDataClientResponse;
import com.vet.hc.api.client.adapter.in.response.PaginatedClientResponse;
import com.vet.hc.api.shared.domain.query.BasicResponse;
import com.vet.hc.api.shared.domain.query.FailureResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

/**
 * Client controller.
 */
@Tag(name = "Client endpoints", description = "Endpoints for clients")
@Path("/client")
@NoArgsConstructor
public class ClientController {
    private Validator validator;

    @Inject
    public ClientController(Validator validator) {
        this.validator = validator;
    }

    /**
     * Get all clients.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The clients paginated
     */
    @Operation(summary = "Get all clients", description = "Get all clients using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "The page and size are empty or size exceeded the limit.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClients(
            @QueryParam("page") @Parameter(required = true, description = "Page number") Integer page,
            @QueryParam("size") @Parameter(required = true, description = "Page size (max 10 elements)") Integer size) {
        return Response.ok().build();
    }

    /**
     * Get client by id.
     */
    @Operation(summary = "Get client by id", description = "Get client by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully.", content = @Content(schema = @Schema(implementation = FullDataClientResponse.class))),
            @ApiResponse(responseCode = "404", description = "The client was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientById(@PathParam("id") Long id) {
        return Response.ok().build();
    }

    /**
     * Create a new client.
     *
     * @param request The client data.
     * @return The created client
     */
    @Operation(summary = "Create a new client", description = "Create a new client.", responses = {
            @ApiResponse(responseCode = "200", description = "The client was created successfully.", content = @Content(schema = @Schema(implementation = FullDataClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClient(CreateClientRequest request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        return Response.ok().build();
    }

    /**
     * Update a client.
     *
     * @param id The client id.
     * @return The updated client
     */
    @Operation(summary = "Update a client", description = "Update a client.", responses = {
            @ApiResponse(responseCode = "200", description = "The client was updated successfully.", content = @Content(schema = @Schema(implementation = FullDataClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The client was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("id") Long id, CreateClientRequest request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        return Response.ok().build();
    }

    /**
     * Update client emails.
     *
     * @param id      The client id.
     * @param request The emails to update.
     * @return The updated client
     */
    @Operation(summary = "Update client emails'", description = "Update client emails'. If an email is removed from the set, it will be deleted from the database. If an email is added to the set without an ID, it will be inserted into the database. If an email is added to the set with an ID, it will be updated in the database. If the client has no emails, the emails set will be empty.", responses = {
            @ApiResponse(responseCode = "200", description = "The client emails' were updated successfully.", content = @Content(schema = @Schema(implementation = FullDataClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client email's data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The client was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}/email")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClientEmails(@PathParam("id") Long id, UpdateClientEmailsRequest request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        return Response.ok().build();
    }

    /**
     * Update client phones.
     *
     * @param id      The client id.
     * @param request The phones to update.
     * @return The updated client
     */
    @Operation(summary = "Update client phones'", description = "Update client phones' If a phone is removed from the set, it will be deleted from the database. If a phone is added to the set without an ID, it will be inserted into the database. If a phone is added to the set with an ID, it will be updated in the database. If the client has no phones, the phones set will be empty.", responses = {
            @ApiResponse(responseCode = "200", description = "The client phones' were updated successfully.", content = @Content(schema = @Schema(implementation = FullDataClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client email's data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The client was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}/phone")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClientPhones(@PathParam("id") Long id, UpdateClientPhonesRequest request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        return Response.ok().build();
    }

    /**
     * Delete a client.
     *
     * @param id The client id.
     * @return The deleted client
     */
    @Operation(summary = "Delete a client", description = "Delete a client.", responses = {
            @ApiResponse(responseCode = "200", description = "The client was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The client was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClient(@PathParam("id") Long id) {
        return Response.ok().build();
    }
}
