package com.vet.hc.api.bill.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vet.hc.api.bill.core.adapter.in.request.CreateBillDto;
import com.vet.hc.api.bill.core.adapter.in.request.UpdateBillDto;
import com.vet.hc.api.bill.core.adapter.in.response.BillResponse;
import com.vet.hc.api.bill.core.adapter.in.response.PaginatedBillResponse;
import com.vet.hc.api.bill.core.application.port.in.CreateBillPort;
import com.vet.hc.api.bill.core.application.port.in.DeleteBillPort;
import com.vet.hc.api.bill.core.application.port.in.FindBillPort;
import com.vet.hc.api.bill.core.application.port.in.GenerateBillExcelPort;
import com.vet.hc.api.bill.core.application.port.in.UpdateBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.util.EnumUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.criteria.FilterOperator;
import com.vet.hc.api.shared.domain.criteria.Order;
import com.vet.hc.api.shared.domain.criteria.OrderType;
import com.vet.hc.api.shared.domain.query.Result;
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
 * Bill controller.
 */
@Tag(name = "Bill", description = "Bill Veterinary")
@Path("/bill")
@NoArgsConstructor
public class BillController {
    private CreateBillPort createBillPort;
    private FindBillPort findBillPort;
    private UpdateBillPort updateBillPort;
    private DeleteBillPort deleteBillPort;
    private GenerateBillExcelPort generateBillExcelPort;

    @Inject
    public BillController(
            CreateBillPort createBillPort,
            FindBillPort findBillPort,
            UpdateBillPort updateBillPort,
            DeleteBillPort deleteBillPort,
            GenerateBillExcelPort generateBillExcelPort) {
        this.createBillPort = createBillPort;
        this.findBillPort = findBillPort;
        this.updateBillPort = updateBillPort;
        this.deleteBillPort = deleteBillPort;
        this.generateBillExcelPort = generateBillExcelPort;
    }

    /**
     * Get all bills.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The bills paginated
     */
    @Operation(summary = "Get all bills", description = "Get all bills using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Bills retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedBillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
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
            return toDetailedFailureResponse(validationErrors);

        Criteria criteria = new Criteria(
                List.of(
                        new Filter("firstName", FilterOperator.CONTAINS, firstName),
                        new Filter("lastName", FilterOperator.CONTAINS, lastName),
                        new Filter("identification", FilterOperator.CONTAINS, identification)),
                Order.of(orderBy, orderType),
                size,
                page);
        var result = findBillPort.match(criteria);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return Response.ok(result.getSuccess()).build();
    }

    /**
     * Get bill by id.
     */
    @Operation(summary = "Get bill by id", description = "Get bill by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Bill retrieved successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bill was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        var result = findBillPort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "Tipo de cita encontrado exitosamente");
    }

    /**
     * Retrieve an Excel file with the bills.
     */
    @Operation(summary = "Generate an Excel file with the bills", description = "Generate an Excel file with the bills.", responses = {
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
                    "Cuentas %d-%d-%d %d_%d_%d.xlsx",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond());

            generateBillExcelPort.generateExcel(outputStream);

            return Response.ok(outputStream.toByteArray())
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .build();
        } catch (IOException e) {
            return toFailureResponse("Error al generar el archivo Excel", Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new bill.
     *
     * @param request The bill data.
     * @return The created bill
     */
    @Operation(summary = "Create a new bill", description = "Create a new bill.", responses = {
            @ApiResponse(responseCode = "200", description = "Bill was created successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bill data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Bill name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateBillDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<BillDto, BillFailure> result = createBillPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "El tipo de cita fue creada exitosamente");
    }

    /**
     * Update a bill.
     *
     * @param id The bill id.
     * @return The updated bill
     */
    @Operation(summary = "Update a bill", description = "Update a bill.", responses = {
            @ApiResponse(responseCode = "200", description = "Bill was updated successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bill data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bill was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Bill name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateBillDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<BillDto, BillFailure> result = updateBillPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "El tipo de cita fue actualizada exitosamente");
    }

    /**
     * Delete a bill.
     *
     * @param id The bill id.
     * @return The deleted bill
     */
    @Operation(summary = "Delete a bill", description = "Delete a bill.", responses = {
            @ApiResponse(responseCode = "200", description = "Bill was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bill was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        Result<Void, BillFailure> result = deleteBillPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("El tipo de cita fue eliminado exitosamente");
    }
}
