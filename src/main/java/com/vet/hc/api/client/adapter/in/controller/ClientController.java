package com.vet.hc.api.client.adapter.in.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.client.adapter.in.request.CreateClientDto;
import com.vet.hc.api.client.adapter.in.request.UpdateFullDataClientDto;
import com.vet.hc.api.client.application.port.in.CreateClientPort;
import com.vet.hc.api.client.application.port.in.DeleteClientPort;
import com.vet.hc.api.client.application.port.in.GenerateClientExcelPort;
import com.vet.hc.api.client.application.port.in.LoadClientPort;
import com.vet.hc.api.client.application.port.in.UpdateClientPort;
import com.vet.hc.api.client.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.patient.domain.response.FullDataClientResponse;
import com.vet.hc.api.patient.domain.response.PaginatedClientResponse;
import com.vet.hc.api.shared.application.util.EnumUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.criteria.FilterOperator;
import com.vet.hc.api.shared.domain.criteria.Order;
import com.vet.hc.api.shared.domain.criteria.OrderType;
import com.vet.hc.api.shared.domain.query.BasicResponse;
import com.vet.hc.api.shared.domain.query.FailureResponse;
import com.vet.hc.api.shared.domain.query.Result;

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
    private CreateClientPort createClientPort;
    private LoadClientPort loadClientPort;
    private UpdateClientPort updateClientPort;
    private DeleteClientPort deleteClientPort;
    private GenerateClientExcelPort generateClientExcelPort;

    private Validator validator;

    @Inject
    public ClientController(
            CreateClientPort createClientPort,
            LoadClientPort loadClientPort,
            UpdateClientPort updateClientPort,
            DeleteClientPort deleteClientPort,
            GenerateClientExcelPort generateClientExcelPort,
            Validator validator) {
        this.createClientPort = createClientPort;
        this.loadClientPort = loadClientPort;
        this.updateClientPort = updateClientPort;
        this.deleteClientPort = deleteClientPort;
        this.generateClientExcelPort = generateClientExcelPort;
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
            @QueryParam("size") @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @QueryParam("order_by") @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @QueryParam("order") @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @QueryParam("first_name") @Parameter(description = "First name") String firstName,
            @QueryParam("last_name") @Parameter(description = "Last name") String lastName,
            @QueryParam("identification") @Parameter(description = "Identification") String identification) {
        OrderType orderType = null;
        if (orderTypeStr != null) {
            try {
                orderType = OrderType.valueOf(orderTypeStr.toUpperCase());
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(FailureResponse.builder()
                                .message("El tipo de orden no es válido, los valores permitidos son: "
                                        + String.join(", ", EnumUtils.getEnumNames(OrderType.class)))
                                .build())
                        .build();
            }
        }

        if (page == null || size == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message("La página y el tamaño son obligatorios")
                            .build())
                    .build();
        }

        else if (size > 10) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message("El tamaño máximo es 10")
                            .build())
                    .build();
        }

        Criteria criteria = new Criteria(
                List.of(
                        new Filter("firstName", FilterOperator.CONTAINS, firstName),
                        new Filter("lastName", FilterOperator.CONTAINS, lastName),
                        new Filter("identification", FilterOperator.CONTAINS, identification)),
                Order.of(orderBy, orderType),
                size,
                page);

        var result = loadClientPort.match(criteria);

        if (result.isFailure()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(result.getSuccess()).build();
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
        Result<FullDataClientDto, ClientFailure> result = loadClientPort.findById(id);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                FullDataClientResponse.builder()
                        .message("Cliente encontrado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    @Operation(summary = "Generate an Excel file with the clients", description = "Generate an Excel file with the clients.", responses = {
            @ApiResponse(responseCode = "200", description = "The Excel file was generated successfully.", content = @Content(schema = @Schema(implementation = InputStream.class))),
    })
    @GET
    @Path("/excel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateExcel() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            LocalDateTime now = LocalDateTime.now();

            String fileName = String.format(
                    "Clientes %d-%d-%d %d_%d_%d.xlsx",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond());

            generateClientExcelPort.generateExcel(outputStream);

            return Response.ok(outputStream.toByteArray())
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(FailureResponse.builder()
                            .message("Error al generar el archivo Excel")
                            .build())
                    .build();
        }
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
    public Response createClient(CreateClientDto request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        Result<FullDataClientDto, ClientFailure> result = createClientPort.create(request);

        if (result.isFailure()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                FullDataClientResponse.builder()
                        .message("Cliente creado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
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
    public Response updateClient(@PathParam("id") Long id, UpdateFullDataClientDto request) {
        if (!id.equals(request.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message("El id del cliente no coincide con el id de la solicitud")
                            .build())
                    .build();
        }

        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        Result<FullDataClientDto, ClientFailure> result = updateClientPort.update(request);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                FullDataClientResponse.builder()
                        .message("Cliente actualizado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
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
        Result<Void, ClientFailure> result = deleteClientPort.deleteById(id);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message("El cliente no fue encontrado")
                            .build())
                    .build();
        }

        return Response.ok(
                BasicResponse.builder()
                        .message("El cliente fue eliminado exitosamente")
                        .build())
                .build();
    }
}
