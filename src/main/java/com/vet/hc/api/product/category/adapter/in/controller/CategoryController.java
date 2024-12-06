package com.vet.hc.api.product.category.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
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

import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
import com.vet.hc.api.product.category.adapter.in.request.CreateCategoryRequest;
import com.vet.hc.api.product.category.adapter.in.request.UpdateCategoryRequest;
import com.vet.hc.api.product.category.application.port.in.CreateCategoryPort;
import com.vet.hc.api.product.category.application.port.in.DeleteCategoryPort;
import com.vet.hc.api.product.category.application.port.in.FindCategoryPort;
import com.vet.hc.api.product.category.application.port.in.UpdateCategoryPort;
import com.vet.hc.api.product.category.application.response.CategoriesResponse;
import com.vet.hc.api.product.category.application.response.CategoryResponse;
import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.domain.validation.SimpleValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Category controller.
 */
@Tag(name = "Category", description = "Product category")
@RestControllerAdapter
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CreateCategoryPort createCategoryPort;
    private final FindCategoryPort loadCategoryPort;
    private final UpdateCategoryPort updateCategoryPort;
    private final DeleteCategoryPort deleteCategoryPort;

    /**
     * Get all categories.
     *
     * @return The categories
     */
    @Operation(summary = "Get all categories", description = "Get all categories.", responses = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully.", content = @Content(schema = @Schema(implementation = CategoriesResponse.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CategoryDto> categories = loadCategoryPort.findAll();

        categories = new CopyOnWriteArrayList<>(categories);
        categories.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));

        return toOkResponse(
                CategoriesResponse.class,
                categories,
                "Categorías encontradas exitosamente");
    }

    /**
     * Create a new category.
     *
     * @param request The category data.
     * @return The created category
     */
    @Operation(summary = "Create a new category", description = "Create a new category.", responses = {
            @ApiResponse(responseCode = "200", description = "The category was created successfully.", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateCategoryRequest request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createCategoryPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                CategoryResponse.class,
                result.getSuccess(),
                "Categoría creada exitosamente");
    }

    /**
     * Update a category.
     *
     * @param id The category id.
     * @return The updated category
     */
    @Operation(summary = "Update a category", description = "Update a category.", responses = {
            @ApiResponse(responseCode = "200", description = "The category was updated successfully.", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The category was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(validate(
                new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                new SimpleValidation(id != null && id.equals(request.getId()), "id path param",
                        "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateCategoryPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                CategoryResponse.class,
                result.getSuccess(),
                "Categoría actualizada exitosamente");
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
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        var result = deleteCategoryPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("La categoría fue eliminada exitosamente");
    }
}