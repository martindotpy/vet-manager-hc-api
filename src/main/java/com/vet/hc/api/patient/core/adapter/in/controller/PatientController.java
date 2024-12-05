package com.vet.hc.api.patient.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFileResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toPaginatedResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vet.hc.api.medicalrecord.core.adapter.in.request.CreateMedicalRecordDto;
import com.vet.hc.api.patient.core.adapter.in.request.CreatePatientDto;
import com.vet.hc.api.patient.core.adapter.in.request.UpdatePatientDto;
import com.vet.hc.api.patient.core.application.port.in.AddMedicalHistoryToPatientPort;
import com.vet.hc.api.patient.core.application.port.in.AddMedicalRecordToPatientPort;
import com.vet.hc.api.patient.core.application.port.in.AddVaccineToPatientPort;
import com.vet.hc.api.patient.core.application.port.in.CreatePatientPort;
import com.vet.hc.api.patient.core.application.port.in.DeletePatientPort;
import com.vet.hc.api.patient.core.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.core.application.port.in.GeneratePatientExcelPort;
import com.vet.hc.api.patient.core.application.port.in.UpdatePatientPort;
import com.vet.hc.api.patient.core.application.response.PaginatedPatientResponse;
import com.vet.hc.api.patient.core.application.response.PatientResponse;
import com.vet.hc.api.patient.medicalhistory.adapter.in.request.CreateMedicalHistoryDto;
import com.vet.hc.api.patient.vaccine.adapter.in.request.CreateVaccineDto;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.util.EnumUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.criteria.FilterOperator;
import com.vet.hc.api.shared.domain.criteria.Order;
import com.vet.hc.api.shared.domain.criteria.OrderType;
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
 * Patient controller.
 */
@Tag(name = "Patient", description = "Veterinary patient")
@Path("/patient")
@NoArgsConstructor
public class PatientController {
    private CreatePatientPort createPatientPort;
    private AddVaccineToPatientPort addVaccineToPatientPort;
    private AddMedicalHistoryToPatientPort addMedicalHistoryToPatientPort;
    private AddMedicalRecordToPatientPort addMedicalRecordToPatientPort;
    private FindPatientPort loadPatientPort;
    private UpdatePatientPort updatePatientPort;
    private DeletePatientPort deletePatientPort;
    private GeneratePatientExcelPort generatePatientExcelPort;

    @Inject
    public PatientController(
            CreatePatientPort createPatientPort,
            AddVaccineToPatientPort addVaccineToPatientPort,
            AddMedicalHistoryToPatientPort addMedicalHistoryToPatientPort,
            AddMedicalRecordToPatientPort addMedicalRecordToPatientPort,
            FindPatientPort loadPatientPort,
            UpdatePatientPort updatePatientPort,
            DeletePatientPort deletePatientPort,
            GeneratePatientExcelPort generatePatientExcelPort) {
        this.createPatientPort = createPatientPort;
        this.addVaccineToPatientPort = addVaccineToPatientPort;
        this.addMedicalHistoryToPatientPort = addMedicalHistoryToPatientPort;
        this.addMedicalRecordToPatientPort = addMedicalRecordToPatientPort;
        this.loadPatientPort = loadPatientPort;
        this.updatePatientPort = updatePatientPort;
        this.deletePatientPort = deletePatientPort;
        this.generatePatientExcelPort = generatePatientExcelPort;
    }

    /**
     * Get all patients paginated.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The patients paginated
     */
    @Operation(summary = "Get all patients paginated", description = "Get all patients using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedPatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByCriteria(
            @QueryParam("page") @Parameter(required = true, description = "Page number (min 1)") Integer page,
            @QueryParam("size") @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @QueryParam("order_by") @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @QueryParam("order") @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @QueryParam("name") @Parameter(description = "Patient name") String patientName,
            @QueryParam("owner_name") @Parameter(description = "Owner name") String ownerName,
            @QueryParam("owner_lastname") @Parameter(description = "Owner lastname") String ownerLastName,
            @QueryParam("owner_identification") @Parameter(description = "Owner identification") String ownerIdentification) {
        var validationErrors = new CopyOnWriteArrayList<ValidationError>();

        OrderType orderType = null;
        if (orderTypeStr != null)
            try {
                orderType = OrderType.valueOf(orderTypeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                validationErrors.add(new ValidationError("order query param",
                        "El tipo de orden no es válido, los valores permitidos son: "
                                + String.join(", ", EnumUtils.getEnumNames(OrderType.class, String::toLowerCase))));
            }

        validationErrors.addAll(validate(
                new SimpleValidation(page == null, "page query param", "La página es obligatoria"),
                new SimpleValidation(page != null && page < 1, "page query param", "La página debe ser mayor a 0"),
                new SimpleValidation(size == null, "size query param", "El tamaño es obligatorio"),
                new SimpleValidation(size != null && size > 10, "size query param", "El tamaño máximo es 10")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Criteria criteria = new Criteria(
                List.of(
                        new Filter("name", FilterOperator.LIKE, patientName),
                        new Filter("owner.firstName", FilterOperator.LIKE, ownerName),
                        new Filter("owner.lastName", FilterOperator.LIKE, ownerLastName),
                        new Filter("owner.identification", FilterOperator.LIKE, ownerIdentification)),
                Order.of(orderBy, orderType),
                size,
                page);
        var result = loadPatientPort.match(criteria);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toPaginatedResponse(
                PaginatedPatientResponse.class,
                result.getSuccess(),
                "Pacientes encontradas exitosamente");
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
    public Response getById(@PathParam("id") Long id) {
        var result = loadPatientPort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                PatientResponse.class,
                result.getSuccess(),
                "Paciente encontrado exitosamente");
    }

    @Operation(summary = "Generate an Excel file with the patients", description = "Generate an Excel file with the patients.", responses = {
            @ApiResponse(responseCode = "200", description = "The Excel file was generated successfully.", content = @Content(schema = @Schema(implementation = InputStream.class))),
            @ApiResponse(responseCode = "500", description = "Error generating the Excel file.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
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

            return toFileResponse(
                    outputStream.toByteArray(),
                    fileName);
        } catch (IOException e) {
            return toFailureResponse(
                    "Error al generar el archivo Excel",
                    Status.INTERNAL_SERVER_ERROR);
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
            @ApiResponse(responseCode = "400", description = "Invalid patient data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreatePatientDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createPatientPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                PatientResponse.class,
                result.getSuccess(),
                "Paciente creado exitosamente");
    }

    /**
     * Add a vaccine to a patient.
     *
     * @param request The vaccine data.
     * @return The updated patient
     */
    @Operation(summary = "Add new vaccine to a patient", description = "Add new vaccine to a patient.", responses = {
            @ApiResponse(responseCode = "200", description = "The vaccine was add successfully.", content = @Content(schema = @Schema(implementation = PatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid vaccine data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/{id}/vaccine")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVaccine(
            @PathParam("id") Long id,
            CreateVaccineDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getPatientId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addVaccineToPatientPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                PatientResponse.class,
                result.getSuccess(),
                "Vacuna agregada exitosamente");
    }

    @Operation(summary = "Add new medical history to a patient", description = "Add new medical history to a patient.", responses = {
            @ApiResponse(responseCode = "200", description = "The medical history was addsuccessfully.", content = @Content(schema = @Schema(implementation = PatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid medical history data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),

    })
    @POST
    @Path("/{id}/medicalhistory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMedicalHistory(
            @PathParam("id") Long id,
            CreateMedicalHistoryDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getPatientId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addMedicalHistoryToPatientPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                PatientResponse.class,
                result.getSuccess(),
                "Historial médico agregada exitosamente");
    }

    @Operation(summary = "Add new medical record to a patient", description = "Add new medical record to a patient.", responses = {
            @ApiResponse(responseCode = "200", description = "The medical record was addsuccessfully.", content = @Content(schema = @Schema(implementation = PatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid medical record data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),

    })
    @POST
    @Path("/{id}/medicalrecord")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(
            @PathParam("id") Long id,
            CreateMedicalRecordDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getPatientId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addMedicalRecordToPatientPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                PatientResponse.class,
                result.getSuccess(),
                "Historial médico agregada exitosamente");
    }

    /**
     * Update a patient.
     *
     * @param id The patient id.
     * @return The updated patient
     */
    @Operation(summary = "Update a patient", description = "Update a patient.", responses = {
            @ApiResponse(responseCode = "200", description = "The patient was updated successfully.", content = @Content(schema = @Schema(implementation = PatientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patient data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The patient was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdatePatientDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updatePatientPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                PatientResponse.class,
                result.getSuccess(),
                "Paciente actualizado exitosamente");
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
    public Response delete(@PathParam("id") Long id) {
        var result = deletePatientPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Paciente eliminado exitosamente");
    }
}