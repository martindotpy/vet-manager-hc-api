package com.vet.hc.api.bill.productsale.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import com.vet.hc.api.bill.productsale.adapter.in.request.UpdateProductSaleDto;
import com.vet.hc.api.bill.productsale.adapter.in.response.ProductSaleResponse;
import com.vet.hc.api.bill.productsale.application.port.in.DeleteProductSalePort;
import com.vet.hc.api.bill.productsale.application.port.in.UpdateProductSalePort;
import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.validation.SimpleValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

/**
 * Product sale controller.
 */
@Tag(name = "Product sale", description = "Bill product sale")
@Path("/product/sale")
@NoArgsConstructor
public class ProductSaleController {
    private UpdateProductSalePort updateProductSalePort;
    private DeleteProductSalePort deleteProductSalePort;

    @Inject
    public ProductSaleController(
            UpdateProductSalePort updateProductSalePort,
            DeleteProductSalePort deleteProductSalePort) {
        this.updateProductSalePort = updateProductSalePort;
        this.deleteProductSalePort = deleteProductSalePort;
    }

    /**
     * Update a product sale.
     *
     * @param id The product sale id.
     * @return The updated product sale
     */
    @Operation(summary = "Update a product sale", description = "Update a product sale.", responses = {
            @ApiResponse(responseCode = "200", description = "Product sale was updated successfully.", content = @Content(schema = @Schema(implementation = ProductSaleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product sale data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product sale was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateProductSaleDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<ProductSaleDto, ProductSaleFailure> result = updateProductSalePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                ProductSaleResponse.class,
                result.getSuccess(),
                "Venta de cita actualizada exitosamente");
    }

    /**
     * Delete a product sale.
     *
     * @param id The product sale id.
     * @return The deleted product sale
     */
    @Operation(summary = "Delete a product sale", description = "Delete a product sale.", responses = {
            @ApiResponse(responseCode = "200", description = "Product sale was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product sale was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        Result<Void, ProductSaleFailure> result = deleteProductSalePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Venta de cita eliminada exitosamente");
    }
}
