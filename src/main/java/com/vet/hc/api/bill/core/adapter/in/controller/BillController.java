package com.vet.hc.api.bill.core.adapter.in.controller;

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

import com.vet.hc.api.bill.appointmentsale.adapter.in.request.CreateAppointmentSaleDto;
import com.vet.hc.api.bill.core.adapter.in.request.CreateBillDto;
import com.vet.hc.api.bill.core.adapter.in.request.UpdateBillDto;
import com.vet.hc.api.bill.core.application.port.in.AddAppointmentSaleToBillPort;
import com.vet.hc.api.bill.core.application.port.in.AddProductSaleToBillPort;
import com.vet.hc.api.bill.core.application.port.in.AddTreatmentSaleToBillPort;
import com.vet.hc.api.bill.core.application.port.in.CreateBillPort;
import com.vet.hc.api.bill.core.application.port.in.DeleteBillPort;
import com.vet.hc.api.bill.core.application.port.in.FindBillPort;
import com.vet.hc.api.bill.core.application.port.in.GenerateBillExcelPort;
import com.vet.hc.api.bill.core.application.port.in.UpdateBillPort;
import com.vet.hc.api.bill.core.application.response.BillResponse;
import com.vet.hc.api.bill.core.application.response.PaginatedBillResponse;
import com.vet.hc.api.bill.productsale.adapter.in.request.CreateProductSaleDto;
import com.vet.hc.api.bill.treatmentsale.adapter.in.request.CreateTreatmentSaleDto;
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
 * Bill controller.
 */
@Tag(name = "Bill", description = "Veterinary bill")
@Path("/bill")
@NoArgsConstructor
public class BillController {
    private CreateBillPort createBillPort;
    private AddAppointmentSaleToBillPort addAppointmentSaleToBillPort;
    private AddTreatmentSaleToBillPort addTreatmentSaleToBillPort;
    private AddProductSaleToBillPort addProductSaleToBillPort;
    private FindBillPort loadBillPort;
    private UpdateBillPort updateBillPort;
    private DeleteBillPort deleteBillPort;
    private GenerateBillExcelPort generateBillExcelPort;

    @Inject
    public BillController(
            CreateBillPort createBillPort,
            AddAppointmentSaleToBillPort addAppointmentToBillPort,
            AddTreatmentSaleToBillPort addTreatmentToBillPort,
            AddProductSaleToBillPort addProductToBillPort,
            FindBillPort loadBillPort,
            UpdateBillPort updateBillPort,
            DeleteBillPort deleteBillPort,
            GenerateBillExcelPort generateBillExcelPort) {
        this.createBillPort = createBillPort;
        this.addAppointmentSaleToBillPort = addAppointmentToBillPort;
        this.addTreatmentSaleToBillPort = addTreatmentToBillPort;
        this.addProductSaleToBillPort = addProductToBillPort;
        this.loadBillPort = loadBillPort;
        this.updateBillPort = updateBillPort;
        this.deleteBillPort = deleteBillPort;
        this.generateBillExcelPort = generateBillExcelPort;
    }

    /**
     * Get all bills paginated.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The bills paginated
     */
    @Operation(summary = "Get all bills paginated", description = "Get all bills using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Bills retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedBillResponse.class))),
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
            @QueryParam("client_name") @Parameter(description = "Client name") String clientName,
            @QueryParam("client_lastname") @Parameter(description = "Client lastname") String clientLastName,
            @QueryParam("client_identification") @Parameter(description = "Client identification") String clientIdentification,
            @QueryParam("appointment_id") @Parameter(description = "Appointment id") Long appointmentId,
            @QueryParam("treatment_id") @Parameter(description = "Treatment id") Long treatmentId,
            @QueryParam("product_id") @Parameter(description = "Product id") Long productId) {
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
                        new Filter("client.firstName", FilterOperator.LIKE, clientName),
                        new Filter("client.lastName", FilterOperator.LIKE, clientLastName),
                        new Filter("client.identification", FilterOperator.LIKE, clientIdentification),
                        new Filter("appointmentSales.id", FilterOperator.LIKE, appointmentId.toString()),
                        new Filter("treatmentSales.id", FilterOperator.LIKE, treatmentId.toString()),
                        new Filter("productSales.id", FilterOperator.LIKE, productId.toString())),
                Order.of(orderBy, orderType),
                size,
                page);
        var result = loadBillPort.match(criteria);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toPaginatedResponse(
                PaginatedBillResponse.class,
                result.getSuccess(),
                "Cuentas encontradas exitosamente");
    }

    /**
     * Get bill by id.
     */
    @Operation(summary = "Get bill by id", description = "Get bill by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Bill retrieved successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "404", description = "The bill was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        var result = loadBillPort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "Cuenta encontrado exitosamente");
    }

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
     * Create a new bill.
     *
     * @param request The bill data.
     * @return The created bill
     */
    @Operation(summary = "Create a new bill", description = "Create a new bill.", responses = {
            @ApiResponse(responseCode = "200", description = "The bill was created successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bill data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateBillDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createBillPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "Cuenta creada exitosamente");
    }

    /**
     * Add a appointment to a bill.
     *
     * @param request The appointment data.
     * @return The updated bill
     */
    @Operation(summary = "Add new appointment to a bill", description = "Add new appointment to a bill.", responses = {
            @ApiResponse(responseCode = "200", description = "The appointment was add successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/{id}/appointment/sale")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAppointmentSale(
            @PathParam("id") Long id,
            CreateAppointmentSaleDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getBillId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addAppointmentSaleToBillPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "Venta de tratamiento agregada exitosamente");
    }

    /**
     * Add a treatment to a bill.
     *
     * @param request The treatment data.
     * @return The updated bill
     */
    @Operation(summary = "Add new treatment to a bill", description = "Add new treatment to a bill.", responses = {
            @ApiResponse(responseCode = "200", description = "The treatment was add successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid treatment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/{id}/treatment/sale")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTreatmentSale(
            @PathParam("id") Long id,
            CreateTreatmentSaleDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getBillId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addTreatmentSaleToBillPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "Venta de tratamiento agregada exitosamente");
    }

    /**
     * Add a product to a bill.
     *
     * @param request The product data.
     * @return The updated bill
     */
    @Operation(summary = "Add new product to a bill", description = "Add new product to a bill.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was add successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/{id}/product/sale")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProductSale(
            @PathParam("id") Long id,
            CreateProductSaleDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getBillId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addProductSaleToBillPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "Venta de tratamiento agregada exitosamente");
    }

    /**
     * Update a bill.
     *
     * @param id The bill id.
     * @return The updated bill
     */
    @Operation(summary = "Update a bill", description = "Update a bill.", responses = {
            @ApiResponse(responseCode = "200", description = "The bill was updated successfully.", content = @Content(schema = @Schema(implementation = BillResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bill data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The bill was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
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

        var result = updateBillPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                BillResponse.class,
                result.getSuccess(),
                "Cuenta actualizada exitosamente");
    }

    /**
     * Delete a bill.
     *
     * @param id The bill id.
     * @return The deleted bill
     */
    @Operation(summary = "Delete a bill", description = "Delete a bill.", responses = {
            @ApiResponse(responseCode = "200", description = "The bill was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The bill was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        var result = deleteBillPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Cuenta eliminado exitosamente");
    }
}