package com.vet.hc.api.product.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toValidationFailureResponse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vet.hc.api.product.adapter.in.request.CreateCategoryDto;
import com.vet.hc.api.product.adapter.in.request.UpdateCategoryDto;
import com.vet.hc.api.product.adapter.in.response.CategoriesResponse;
import com.vet.hc.api.product.adapter.in.response.CategoryResponse;
import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.product.application.port.in.CreateCategoryPort;
import com.vet.hc.api.product.application.port.in.DeleteCategoryPort;
import com.vet.hc.api.product.application.port.in.FindCategoryPort;
import com.vet.hc.api.product.application.port.in.UpdateCategoryPort;
import com.vet.hc.api.product.domain.failure.CategoryFailure;
import com.vet.hc.api.shared.domain.query.BasicResponse;
import com.vet.hc.api.shared.domain.query.FailureResponse;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.query.ValidationErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

/**
 * Category controller.
 */
@Tag(name = "Category", description = "Product category")
@Path("/category")
@NoArgsConstructor
public class CategoryController {
    private CreateCategoryPort createCategoryPort;
    private FindCategoryPort loadCategoryPort;
    private UpdateCategoryPort updateCategoryPort;
    private DeleteCategoryPort deleteCategoryPort;

    @Inject
    public CategoryController(
            CreateCategoryPort createCategoryPort,
            FindCategoryPort loadCategoryPort,
            UpdateCategoryPort updateCategoryPort,
            DeleteCategoryPort deleteCategoryPort) {
        this.createCategoryPort = createCategoryPort;
        this.loadCategoryPort = loadCategoryPort;
        this.updateCategoryPort = updateCategoryPort;
        this.deleteCategoryPort = deleteCategoryPort;
    }

    /**
     * Get all categories.
     *
     * @return The categories paginated
     */
    @Operation(summary = "Get all categories", description = "Get all categories using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully.", content = @Content(schema = @Schema(implementation = CategoriesResponse.class))),
            @ApiResponse(responseCode = "400", description = "The page and size are empty or size exceeded the limit.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        List<CategoryDto> categories = loadCategoryPort.findAll();

        categories = new CopyOnWriteArrayList<>(categories);
        categories.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));

        return Response.ok(
                CategoriesResponse.builder()
                        .message("Categorías encontradas")
                        .content(categories)
                        .build())
                .build();
    }

    /**
     * Create a new category.
     *
     * @param request The category data.
     * @return The created category
     */
    @Operation(summary = "Create a new category", description = "Create a new category.", responses = {
            @ApiResponse(responseCode = "200", description = "The category was created successfully.", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category data.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(CreateCategoryDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toValidationFailureResponse(validationErrors);

        Result<CategoryDto, CategoryFailure> result = createCategoryPort.create(request);

        if (result.isFailure()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                CategoryResponse.builder()
                        .message("Categoría creada exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    /**
     * Update a category.
     *
     * @param id The category id.
     * @return The updated category
     */
    @Operation(summary = "Update a category", description = "Update a category.", responses = {
            @ApiResponse(responseCode = "200", description = "The category was updated successfully.", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category data.", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "The category was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") Long id, UpdateCategoryDto request) {
        if (!id.equals(request.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(FailureResponse.builder()
                            .message("El id de la categoría no coincide con el id de la petición")
                            .build())
                    .build();
        }

        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toValidationFailureResponse(validationErrors);

        Result<CategoryDto, CategoryFailure> result = updateCategoryPort.update(request);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message(result.getFailure().getMessage())
                            .build())
                    .build();
        }

        return Response.ok(
                CategoryResponse.builder()
                        .message("Categoría actualizada exitosamente")
                        .content(result.getSuccess())
                        .build())
                .build();
    }

    /**
     * Delete a category.
     *
     * @param id The category id.
     * @return The deleted category
     */
    @Operation(summary = "Delete a category", description = "Delete a category.", responses = {
            @ApiResponse(responseCode = "200", description = "The category was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The category was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("id") Long id) {
        Result<Void, CategoryFailure> result = deleteCategoryPort.deleteById(id);

        if (result.isFailure()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(FailureResponse.builder()
                            .message("La categoría no fue encontrada")
                            .build())
                    .build();
        }

        return Response.ok(
                BasicResponse.builder()
                        .message("La categoría fue eliminada exitosamente")
                        .build())
                .build();
    }
}