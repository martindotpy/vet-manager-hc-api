package com.vet.hc.api.patient.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.patient.adapter.in.request.CreatePatientDto;
import com.vet.hc.api.patient.adapter.in.request.UpdatePatientDto;
import com.vet.hc.api.patient.application.port.in.CreatePatientPort;
import com.vet.hc.api.patient.application.port.in.DeletePatientPort;
import com.vet.hc.api.patient.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.application.port.in.GeneratePatientExcelPort;
import com.vet.hc.api.patient.application.port.in.UpdatePatientPort;
import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.patient.domain.failure.PatientFailure;
import com.vet.hc.api.patient.domain.response.PaginatedPatientResponse;
import com.vet.hc.api.patient.domain.response.PatientResponse;
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
import jakarta.ws.rs.core.Response.Status;
import lombok.NoArgsConstructor;

/**
 * Patient controller.
 */
@Tag(name = "Patient", description = "Endpoints for patients")
@Path("/patient")
@NoArgsConstructor
public class PatientController {
    private CreatePatientPort createPatientPort;
    private FindPatientPort findPatientPort;
    private UpdatePatientPort updatePatientPort;
    private DeletePatientPort deletePatientPort;
    private GeneratePatientExcelPort generatePatientExcelPort;

    private Validator validator;

    @Inject
    public PatientController(
            CreatePatientPort createPatientPort,
            FindPatientPort findPatientPort,
            UpdatePatientPort updatePatientPort,
            DeletePatientPort deletePatientPort,
            GeneratePatientExcelPort generatePatientExcelPort,
            Validator validator) {
        this.createPatientPort = createPatientPort;
        this.findPatientPort = findPatientPort;
        this.updatePatientPort = updatePatientPort;
        this.deletePatientPort = deletePatientPort;
        this.generatePatientExcelPort = generatePatientExcelPort;
        this.validator = validator;
    }

    /**
     * Get all patients.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The patients paginated
     */
    @Operation(summary = "Get all patients", description = "Get all patients using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedPatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "The page and size are empty or size exceeded the limit.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatients(
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
                return toFailureResponse("El tipo de orden no es válido, los valores permitidos son: "
                        + String.join(", ", EnumUtils.getEnumNames(OrderType.class, String::toLowerCase)),
                        Status.BAD_REQUEST);
            }
        }

        if (page == null || size == null)
            return toFailureResponse("La página y el tamaño son obligatorios", Status.BAD_REQUEST);

        else if (size > 10)
            return toFailureResponse("El tamaño máximo es 10", Status.BAD_REQUEST);

        Criteria criteria = new Criteria(
                List.of(
                        new Filter("firstName", FilterOperator.CONTAINS, firstName),
                        new Filter("lastName", FilterOperator.CONTAINS, lastName),
                        new Filter("identification", FilterOperator.CONTAINS, identification)),
                Order.of(orderBy, orderType),
                size,
                page);

        var result = findPatientPort.match(criteria);

        if (result.isFailure())
            return toFailureResponse(result.getFailure(), Status.BAD_REQUEST);

        return Response
                .ok(PaginatedPatientResponse.from(result.getSuccess(), "Patients retrieved successfully"))
                .build();
    }

    /**
     * Get patient by id.
     */
    @Operation(summary = "Get patient by id", description = "Get patient by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Patient retrieved successfully.", content = @Content(schema = @Schema(implementation = PatientResponse.class))),
            @ApiResponse(responseCode = "404", description = "The patient was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("id") Long id) {
        Result<PatientDto, PatientFailure> result = findPatientPort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure(), Status.NOT_FOUND);

        return Response.ok(
                PatientResponse.builder()
                        .message("Paciente encontrado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    @Operation(summary = "Generate an Excel file with the patients", description = "Generate an Excel file with the patients.", responses = {
            @ApiResponse(responseCode = "200", description = "The Excel file was generated successfully.", content = @Content(schema = @Schema(implementation = InputStream.class))),
    })
    @GET
    @Path("/excel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateExcel() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            LocalDateTime now = LocalDateTime.now();

            String fileName = String.format(
                    "Pacientes %d-%d-%d %d_%d_%d.xlsx",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond());

            generatePatientExcelPort.generateExcel(outputStream);

            return Response.ok(outputStream.toByteArray())
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .build();
        } catch (IOException e) {
            return toFailureResponse("Error al generar el archivo Excel", Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new patient.
     *
     * @param request The patient data.
     * @return The created patient
     */
    @Operation(summary = "Create a new patient", description = "Create a new patient.", responses = {
            @ApiResponse(responseCode = "200", description = "The patient was created successfully.", content = @Content(schema = @Schema(implementation = PatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patient data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPatient(CreatePatientDto request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty())
            return toFailureResponse(violations.iterator().next().getMessage(), Status.BAD_REQUEST);

        Result<PatientDto, PatientFailure> result = createPatientPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure(), Status.BAD_REQUEST);

        return Response.ok(
                PatientResponse.builder()
                        .message("Paciente creado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    /**
     * Update a patient.
     *
     * @param id The patient id.
     * @return The updated patient
     */
    @Operation(summary = "Update a patient", description = "Update a patient.", responses = {
            @ApiResponse(responseCode = "200", description = "The patient was updated successfully.", content = @Content(schema = @Schema(implementation = PatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patient data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The patient was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("id") Long id, UpdatePatientDto request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty())
            return toFailureResponse(violations.iterator().next().getMessage(), Status.BAD_REQUEST);

        Result<PatientDto, PatientFailure> result = updatePatientPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure(), Status.NOT_FOUND);

        return Response.ok(PatientResponse.builder().message("Paciente actualizado exitosamente")
                .content(result.getSuccess()).build()).build();
    }

    /**
     * Delete a patient.
     *
     * @param id The patient id.
     * @return The deleted patient
     */
    @Operation(summary = "Delete a patient", description = "Delete a patient.", responses = {
            @ApiResponse(responseCode = "200", description = "The patient was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The patient was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePatient(@PathParam("id") Long id) {
        Result<Void, PatientFailure> result = deletePatientPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure(), Status.NOT_FOUND);

        return Response.ok(
                BasicResponse.builder()
                        .message("El paciente fue eliminado exitosamente")
                        .build())
                .build();
    }
}
