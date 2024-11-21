package com.vet.hc.api.patient.specie.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toValidationFailureResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.util.concurrent.CopyOnWriteArrayList;

import com.vet.hc.api.patient.specie.adapter.in.request.CreateSpecieDto;
import com.vet.hc.api.patient.specie.adapter.in.request.UpdateSpecieDto;
import com.vet.hc.api.patient.specie.application.port.in.CreateSpeciePort;
import com.vet.hc.api.patient.specie.application.port.in.DeleteSpeciePort;
import com.vet.hc.api.patient.specie.application.port.in.FindSpeciePort;
import com.vet.hc.api.patient.specie.application.port.in.UpdateSpeciePort;
import com.vet.hc.api.patient.specie.application.response.SpecieResponse;
import com.vet.hc.api.patient.specie.application.response.SpeciesResponse;
import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.util.EnumUtils;
import com.vet.hc.api.shared.domain.criteria.OrderType;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.query.ValidationErrorResponse;
import com.vet.hc.api.shared.domain.validation.SimpleValidation;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
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
import jakarta.ws.rs.core.Response.Status;
import lombok.NoArgsConstructor;

/**
 * Specie controller.
 */
@Tag(name = "Specie", description = "Veterinary species (pets)")
@Path("/specie")
@NoArgsConstructor
public class SpecieController {
    private CreateSpeciePort createSpeciePort;
    private FindSpeciePort findSpeciePort;
    private UpdateSpeciePort updateSpeciePort;
    private DeleteSpeciePort deleteSpeciePort;

    @Inject
    public SpecieController(
            CreateSpeciePort createSpeciePort,
            FindSpeciePort findSpeciePort,
            UpdateSpeciePort updateSpeciePort,
            DeleteSpeciePort deleteSpeciePort) {
        this.createSpeciePort = createSpeciePort;
        this.findSpeciePort = findSpeciePort;
        this.updateSpeciePort = updateSpeciePort;
        this.deleteSpeciePort = deleteSpeciePort;
    }

    /**
     * Get all species.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The species paginated
     */
    @Operation(summary = "Get all species", description = "Get all species using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Species retrieved successfully.", content = @Content(schema = @Schema(implementation = SpeciesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBy(
            @QueryParam("page") @Parameter(required = true, description = "Page number") Integer page,
            @QueryParam("size") @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @QueryParam("order_by") @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @QueryParam("order") @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @QueryParam("first_name") @Parameter(description = "First name") String firstName,
            @QueryParam("last_name") @Parameter(description = "Last name") String lastName,
            @QueryParam("identification") @Parameter(description = "Identification") String identification) {
        var validationErrors = new CopyOnWriteArrayList<ValidationError>();

        OrderType orderType = null;
        try {
            orderType = OrderType.valueOf(orderTypeStr.toUpperCase()); // Potentially throws NullPointerException and
                                                                       // IllegalArgumentException
        } catch (NullPointerException | IllegalArgumentException e) {
            validationErrors.add(new ValidationError("order query param",
                    "El tipo de orden no es válido, los valores permitidos son: "
                            + String.join(", ", EnumUtils.getEnumNames(OrderType.class, String::toLowerCase))));
        }

        validationErrors.addAll(
                validate(
                        new SimpleValidation(page == null, "page query param", "La página es obligatoria"),
                        new SimpleValidation(size == null, "size query param", "El tamaño es obligatorio"),
                        new SimpleValidation(size != null && size > 10, "size query param", "El tamaño máximo es 10")));

        if (!validationErrors.isEmpty())
            return toValidationFailureResponse(validationErrors);

        var result = findSpeciePort.findAll();

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return Response
                .ok(result.getSuccess())
                .build();
    }

    /**
     * Get specie by id.
     */
    @Operation(summary = "Get specie by id", description = "Get specie by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Specie retrieved successfully.", content = @Content(schema = @Schema(implementation = SpecieResponse.class))),
            @ApiResponse(responseCode = "404", description = "The specie was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        Result<SpecieDto, SpecieFailure> result = findSpeciePort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return Response.ok(
                SpecieResponse.builder()
                        .message("Paciente encontrado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    /**
     * Create a new specie.
     *
     * @param request The specie data.
     * @return The created specie
     */
    @Operation(summary = "Create a new specie", description = "Create a new specie.", responses = {
            @ApiResponse(responseCode = "200", description = "The specie was created successfully.", content = @Content(schema = @Schema(implementation = SpecieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid specie data.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateSpecieDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toValidationFailureResponse(validationErrors);

        Result<SpecieDto, SpecieFailure> result = createSpeciePort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure(), Status.BAD_REQUEST);

        return Response.ok(
                SpecieResponse.builder()
                        .message("Paciente creado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    /**
     * Update a specie.
     *
     * @param id The specie id.
     * @return The updated specie
     */
    @Operation(summary = "Update a specie", description = "Update a specie.", responses = {
            @ApiResponse(responseCode = "200", description = "The specie was updated successfully.", content = @Content(schema = @Schema(implementation = SpecieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid specie data.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "The specie was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateSpecieDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toValidationFailureResponse(validationErrors);

        Result<SpecieDto, SpecieFailure> result = updateSpeciePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return Response.ok(SpecieResponse.builder().message("Paciente actualizado exitosamente")
                .content(result.getSuccess()).build()).build();
    }

    /**
     * Delete a specie.
     *
     * @param id The specie id.
     * @return The deleted specie
     */
    @Operation(summary = "Delete a specie", description = "Delete a specie.", responses = {
            @ApiResponse(responseCode = "200", description = "The specie was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The specie was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSpecie(@PathParam("id") Long id) {
        Result<Void, SpecieFailure> result = deleteSpeciePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return Response.ok(
                BasicResponse.builder()
                        .message("El paciente fue eliminado exitosamente")
                        .build())
                .build();
    }
}
