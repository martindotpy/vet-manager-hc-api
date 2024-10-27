package com.vet.hc.api.product.adapter.in.controller;

import com.vet.hc.api.product.adapter.in.request.CreateProductRequest;
import com.vet.hc.api.product.adapter.in.request.UpdateProductRequest;
import com.vet.hc.api.product.adapter.in.response.PaginatedProductResponse;
import com.vet.hc.api.product.adapter.in.response.ProductResponse;
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
 * Product controller.
 */
@Tag(name = "Product endpoints", description = "Endpoints for products")
@Path("/product")
@NoArgsConstructor
public class ProductController {
    private Validator validator;

    @Inject
    public ProductController(Validator validator) {
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
            @ApiResponse(responseCode = "400", description = "The page and size are empty or size exceeded the limit.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(
            @QueryParam("page") @Parameter(required = true, description = "Page number") Integer page,
            @QueryParam("size") @Parameter(required = true, description = "Page size (max 10 elements)") Integer size) {
        return Response.ok().build();
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
        return Response.ok().build();
    }

    /**
     * Create a new product.
     *
     * @param request The product data.
     * @return The created product
     */
    @Operation(summary = "Create a new product", description = "Create a new product.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was created successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(CreateProductRequest request) {
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
     * Update a product.
     *
     * @param id The product id.
     * @return The updated product
     */
    @Operation(summary = "Update a product", description = "Update a product.", responses = {
            @ApiResponse(responseCode = "200", description = "The product was updated successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The product was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") Long id, UpdateProductRequest updateProductRequest) {
        return Response.ok().build();
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
        return Response.ok().build();
    }
}
