package com.vet.hc.api.product.adapter.in.controller;

import java.util.List;

import com.vet.hc.api.product.adapter.in.request.CreateProductDto;
import com.vet.hc.api.product.adapter.in.request.UpdateProductDto;
import com.vet.hc.api.product.adapter.in.response.PaginatedProductResponse;
import com.vet.hc.api.product.adapter.in.response.ProductResponse;
import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.application.port.in.CreateProductPort;
import com.vet.hc.api.product.application.port.in.DeleteProductPort;
import com.vet.hc.api.product.application.port.in.FindProductPort;
import com.vet.hc.api.product.application.port.in.UpdateProductPort;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.util.EnumUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.criteria.FilterOperator;
import com.vet.hc.api.shared.domain.criteria.Order;
import com.vet.hc.api.shared.domain.criteria.OrderType;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.query.ValidationErrorResponse;

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
 * Product controller.
 */
@Tag(name = "Product", description = "Veterinary product")
@Path("/product")
@NoArgsConstructor
public class ProductController {
    private CreateProductPort createProductPort;
    private FindProductPort loadProductPort;
    private UpdateProductPort updateProductPort;
    private DeleteProductPort deleteProductPort;
    private Validator validator;

    @Inject
    public ProductController(
            CreateProductPort createProductPort,
            FindProductPort loadProductPort,
            UpdateProductPort updateProductPort,
            DeleteProductPort deleteProductPort,
            Validator validator) {
        this.createProductPort = createProductPort;
        this.loadProductPort = loadProductPort;
        this.updateProductPort = updateProductPort;
        this.deleteProductPort = deleteProductPort;
        this.validator = validator;
    }

    /**
     * Get all products.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The products paginated
     */
    @Operation(summary = "Get all products", description = "Get all products using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "The page and size are empty or size exceeded the limit.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(
            @QueryParam("page") @Parameter(required = true, description = "Page number") Integer page,
            @QueryParam("size") @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @QueryParam("order_by") @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @QueryParam("order") @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @QueryParam("category_id") @Parameter(description = "The categories of the product") final List<Integer> categoryIds,
            @QueryParam("name") @Parameter(description = "Product name") String name) {
        OrderType orderType = null;
        if (orderTypeStr != null)
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
                List.of(new Filter("name", FilterOperator.CONTAINS, name)),
                Order.of(orderBy, orderType),
                size,
                page);

        var result = loadProductPort.match(criteria, categoryIds);

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
     * Get product by id.
     */
    @Operation(summary = "Get product by id", description = "Get product by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") Long id) {
        Result<ProductDto, ProductFailure> result = loadProductPort.findById(id);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                ProductResponse.builder()
                        .message("Producto encontrado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    /**
     * Create a new product.
     *
     * @param request The product data.
     * @return The created product
     */
    @Operation(summary = "Create a new product", description = "Create a new product.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was created successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(CreateProductDto request) {
        var violations = validator.validate(request);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(violations.iterator().next().getMessage())
                            .build())
                    .build();
        }

        Result<ProductDto, ProductFailure> result = createProductPort.create(request);

        if (result.isFailure()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                ProductResponse.builder()
                        .message("Producto creado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    /**
     * Update a product.
     *
     * @param id The product id.
     * @return The updated product
     */
    @Operation(summary = "Update a product", description = "Update a product.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was updated successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") Long id, UpdateProductDto request) {
        if (!id.equals(request.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message("El id del producto no coincide con el id de la petición")
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

        Result<ProductDto, ProductFailure> result = updateProductPort.update(request);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                ProductResponse.builder()
                        .message("Producto actualizado exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
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
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") Long id) {
        Result<Void, ProductFailure> result = deleteProductPort.deleteById(id);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message("El producto no fue encontrado")
                            .build())
                    .build();
        }

        return Response.ok(
                BasicResponse.builder()
                        .message("El producto fue eliminado exitosamente")
                        .build())
                .build();
    }
}