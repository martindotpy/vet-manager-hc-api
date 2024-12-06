package com.vet.hc.api.client.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFileResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toPaginatedResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
import com.vet.hc.api.client.core.adapter.in.request.CreateClientRequest;
import com.vet.hc.api.client.core.adapter.in.request.UpdateFullDataClientRequest;
import com.vet.hc.api.client.core.application.port.in.CreateClientPort;
import com.vet.hc.api.client.core.application.port.in.DeleteClientPort;
import com.vet.hc.api.client.core.application.port.in.FindClientPort;
import com.vet.hc.api.client.core.application.port.in.GenerateClientExcelPort;
import com.vet.hc.api.client.core.application.port.in.UpdateClientPort;
import com.vet.hc.api.client.core.application.response.FullDataClientResponse;
import com.vet.hc.api.client.core.application.response.PaginatedClientResponse;
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
import lombok.RequiredArgsConstructor;

/**
 * Client controller.
 */
@Tag(name = "Client", description = "Veterinary clients")
@RestControllerAdapter
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final CreateClientPort createClientPort;
    private final FindClientPort loadClientPort;
    private final UpdateClientPort updateClientPort;
    private final DeleteClientPort deleteClientPort;
    private final GenerateClientExcelPort generateClientExcelPort;

    /**
     * Get all clients.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The clients paginated
     */
    @Operation(summary = "Get all clients", description = "Get all clients using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @GetMapping
    public ResponseEntity<?> getBy(
            @RequestParam @Parameter(required = true, description = "Page number (min 1)") Integer page,
            @RequestParam @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @RequestParam(value = "order_by", required = false) @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @RequestParam(value = "order", required = false) @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @RequestParam(value = "first_name", required = false) @Parameter(description = "First name") String firstName,
            @RequestParam(value = "last_name", required = false) @Parameter(description = "Last name") String lastName,
            @RequestParam(value = "identification", required = false) @Parameter(description = "Identification") String identification) {
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

        validationErrors.addAll(
                validate(
                        new SimpleValidation(page == null, "page query param", "La página es obligatoria"),
                        new SimpleValidation(size == null, "size query param", "El tamaño es obligatorio"),
                        new SimpleValidation(size != null && size > 10, "size query param", "El tamaño máximo es 10")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Criteria criteria = new Criteria(
                List.of(
                        new Filter("firstName", FilterOperator.LIKE, firstName),
                        new Filter("lastName", FilterOperator.LIKE, lastName),
                        new Filter("identification", FilterOperator.LIKE, identification)),
                Order.of(orderBy, orderType),
                size,
                page);
        var result = loadClientPort.match(criteria);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toPaginatedResponse(
                PaginatedClientResponse.class,
                result.getSuccess(),
                "Clientes encontrados exitosamente");
    }

    /**
     * Get client by id.
     */
    @Operation(summary = "Get client by id", description = "Get client by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully.", content = @Content(schema = @Schema(implementation = FullDataClientResponse.class))),
            @ApiResponse(responseCode = "404", description = "The client was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GetMapping("/{id}")

    public ResponseEntity<?> getById(@PathVariable Long id) {
        var result = loadClientPort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                FullDataClientResponse.class,
                result.getSuccess(),
                "Cliente encontrado exitosamente");
    }

    @Operation(summary = "Generate an Excel file with the clients", description = "Generate an Excel file with the clients.", responses = {
            @ApiResponse(responseCode = "200", description = "The Excel file was generated successfully.", content = @Content(schema = @Schema(implementation = InputStream.class))),
            @ApiResponse(responseCode = "500", description = "Error generating the Excel file.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GetMapping("/excel")

    public ResponseEntity<?> generateExcel() {
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

            return toFileResponse(
                    outputStream.toByteArray(),
                    fileName);
        } catch (IOException e) {
            return toFailureResponse(
                    "Error al generar el archivo Excel",
                    HttpStatus.INTERNAL_SERVER_ERROR);
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
            @ApiResponse(responseCode = "400", description = "Invalid client data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateClientRequest request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createClientPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                FullDataClientResponse.class,
                result.getSuccess(),
                "Cliente creado exitosamente");
    }

    /**
     * Update a client.
     *
     * @param id The client id.
     * @return The updated client
     */
    @Operation(summary = "Update a client", description = "Update a client.", responses = {
            @ApiResponse(responseCode = "200", description = "The client was updated successfully.", content = @Content(schema = @Schema(implementation = FullDataClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The client was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateFullDataClientRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateClientPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                FullDataClientResponse.class,
                result.getSuccess(),
                "Cliente actualizado exitosamente");
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
    @DeleteMapping("/{id}")

    public ResponseEntity<?> delete(@PathVariable Long id) {
        var result = deleteClientPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Cliente eliminado exitosamente");
    }
}
