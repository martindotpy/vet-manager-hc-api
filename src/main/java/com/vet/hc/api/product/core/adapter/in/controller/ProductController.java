package com.vet.hc.api.product.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toPaginatedResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
import com.vet.hc.api.product.core.adapter.in.request.CreateProductRequest;
import com.vet.hc.api.product.core.adapter.in.request.UpdateProductRequest;
import com.vet.hc.api.product.core.application.port.in.CreateProductPort;
import com.vet.hc.api.product.core.application.port.in.DeleteProductPort;
import com.vet.hc.api.product.core.application.port.in.FindProductPort;
import com.vet.hc.api.product.core.application.port.in.UpdateProductPort;
import com.vet.hc.api.product.core.application.response.PaginatedProductResponse;
import com.vet.hc.api.product.core.application.response.ProductResponse;
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
 * Product controller.
 */
@Tag(name = "Product", description = "Veterinary products")
@RestControllerAdapter
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductPort createProductPort;
    private final FindProductPort loadProductPort;
    private final UpdateProductPort updateProductPort;
    private final DeleteProductPort deleteProductPort;

    /**
     * Get all products.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The products paginated
     */
    @Operation(summary = "Get all products", description = "Get all products using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "The page and size are empty or size exceeded the limit.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAllByCriteria(
            @RequestParam @Parameter(required = true, description = "Page number (min 1)") Integer page,
            @RequestParam @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @RequestParam(value = "order_by", required = false) @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @RequestParam(value = "order", required = false) @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @RequestParam(value = "category_id", required = false) @Parameter(description = "The categories of the product") final List<Integer> categoryIds,
            @RequestParam(value = "name", required = false) @Parameter(description = "Product name") String name) {
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
                List.of(new Filter("name", FilterOperator.LIKE, name)),
                Order.of(orderBy, orderType),
                size,
                page);

        var result = loadProductPort.match(criteria, categoryIds);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toPaginatedResponse(
                PaginatedProductResponse.class,
                result.getSuccess(),
                "Productos encontrados exitosamente");
    }

    /**
     * Get product by id.
     */
    @Operation(summary = "Get product by id", description = "Get product by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GetMapping("/{id}")

    public ResponseEntity<?> getById(@PathVariable Long id) {
        var result = loadProductPort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                ProductResponse.class,
                result.getSuccess(),
                "Producto encontrado exitosamente");
    }

    /**
     * Create a new product.
     *
     * @param request The product data.
     * @return The created product
     */
    @Operation(summary = "Create a new product", description = "Create a new product.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was created successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateProductRequest request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createProductPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                ProductResponse.class,
                result.getSuccess(),
                "Producto creado exitosamente");
    }

    /**
     * Update a product.
     *
     * @param id The product id.
     * @return The updated product
     */
    @Operation(summary = "Update a product", description = "Update a product.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was updated successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(validate(
                new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                new SimpleValidation(id != null && id.equals(request.getId()), "id path param",
                        "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateProductPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                ProductResponse.class,
                result.getSuccess(),
                "Producto actualizado exitosamente");
    }

    /**
     * Delete a product.
     *
     * @param id The product id.
     * @return The deleted product
     */
    @Operation(summary = "Delete a product", description = "Delete a product.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        var result = deleteProductPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Producto eliminado exitosamente");
    }
}