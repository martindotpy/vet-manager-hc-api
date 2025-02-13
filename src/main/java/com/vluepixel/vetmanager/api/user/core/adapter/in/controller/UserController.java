package com.vluepixel.vetmanager.api.user.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.okPaginated;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.in;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.like;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vluepixel.vetmanager.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.FailureResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.OrderType;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.ValidStateValidation;
import com.vluepixel.vetmanager.api.user.core.adapter.in.response.PaginatedUserResponse;
import com.vluepixel.vetmanager.api.user.core.adapter.in.response.UserResponse;
import com.vluepixel.vetmanager.api.user.core.application.port.in.DeleteUserPort;
import com.vluepixel.vetmanager.api.user.core.application.port.in.FindUserPort;
import com.vluepixel.vetmanager.api.user.core.application.port.in.UpdateUserEmailPort;
import com.vluepixel.vetmanager.api.user.core.application.port.in.UpdateUserPort;
import com.vluepixel.vetmanager.api.user.core.domain.model.enums.UserRole;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserEmailRequest;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * User controller.
 */
@Tag(name = "User", description = "User operations. Only admin can access this endpoints")
@RestControllerAdapter
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final GetCurrentUserPort getCurrentUserPort;

    private final FindUserPort findUserPort;
    private final UpdateUserPort updateCurrentUserPort;
    private final DeleteUserPort deleteUserPort;

    private final UpdateUserEmailPort updateUserEmailPort;

    /**
     * Find all users.
     *
     * @param page       the page.
     * @param size       the size.
     * @param order      the order.
     * @param order_by   the order by.
     * @param id         the id.
     * @param first_name the first name.
     * @param last_name  the last name.
     * @param email      the email.
     * @return the response.
     */
    @Operation(summary = "Find all users", description = "Find all users", responses = {
            @ApiResponse(responseCode = "200", description = "Users found successfully", content = @Content(schema = @Schema(implementation = PaginatedUserResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @GetMapping
    public ResponseEntity<PaginatedUserResponse> findAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false) String order_by,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String first_name,
            @RequestParam(required = false) String last_name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserRole role) {
        return okPaginated(() -> findUserPort.findPaginatedBy(
                PaginatedCriteria.of(
                        page,
                        size,
                        Order.of(order, order_by),
                        like("id", id),
                        like("firstName", first_name),
                        like("lastName", last_name),
                        like("email", email),
                        in("roles", role))),
                "Usuarios encontrados correctamente",
                InvalidStateValidation.of(
                        order != null && order_by == null,
                        "query.order",
                        "El campo para ordenar no puede ser nulo cuando se ha definido un orden"),
                InvalidStateValidation.of(
                        page < 1,
                        "query.page",
                        "La página no puede ser menor a 1"),
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"),
                InvalidStateValidation.of(
                        size < 1,
                        "query.size",
                        "El tamaño no puede ser menor a 1"));
    }

    /**
     * Update the current user.
     *
     * @param request the request.
     * @return the response
     */
    @Operation(summary = "Update the current user", description = "Update the current user with the given data", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "403", description = "Only admin can update user information", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping
    public ResponseEntity<?> updateCurrentUser(@RequestBody UpdateUserRequest request) {
        return ok(() -> updateCurrentUserPort.updateCurrentUser(request),
                "Usuario actualizado correctamente",
                ValidationRequest.of(request),
                ValidStateValidation.of(
                        request.getId().equals(getCurrentUserPort.get().getId()),
                        "path.id",
                        "Id del usuario y del cuerpo no coinciden"));
    }

    /**
     * Update the user by id.
     *
     * @param request the request.
     * @return the response
     */
    @Operation(summary = "Update the user by id", description = "Update the provided user with the given data", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "403", description = "Only admin can update user information", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and #id != principal.id")
    public ResponseEntity<?> update(@RequestBody UpdateUserRequest request, @PathVariable Long id) {
        return ok(() -> updateCurrentUserPort.update(request),
                "Usuario actualizado correctamente",
                ValidationRequest.of(request),
                ValidStateValidation.of(
                        request.getId().equals(id),
                        "path.id",
                        "Id de la ruta y del cuerpo no coinciden"));
    }

    /**
     * Delete the user by id.
     *
     * @param id the id.
     * @return the response.
     */
    @Operation(summary = "Delete the user by id", description = "Delete the user by id", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "403", description = "Only admin can delete user", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ok(() -> deleteUserPort.deleteById(id),
                "Usuario eliminado correctamente");
    }

    // Email
    /**
     * Update the current user email.
     *
     * @param request the request.
     * @return the response
     */
    @Operation(summary = "Update the current user email", description = "Update the current user email with the given data", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "403", description = "Only admin can update user information", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping("/email")
    public ResponseEntity<?> updateCurrentUserEmail(@RequestBody UpdateUserEmailRequest request) {
        return ok(() -> updateUserEmailPort.updateCurrentUser(request),
                "Usuario actualizado correctamente",
                ValidationRequest.of(request),
                ValidStateValidation.of(
                        request.getId().equals(getCurrentUserPort.get().getId()),
                        "path.id",
                        "Id del usuario y del cuerpo no coinciden"));
    }

    /**
     * Update the user email by id.
     *
     * @param request the request.
     * @return the response
     */
    @Operation(summary = "Update the user email by id", description = "Update the provided user email with the given data", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "403", description = "Only admin can update user information", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping("/{id}/email")
    @PreAuthorize("hasRole('ADMIN') and #id != principal.id")
    public ResponseEntity<?> updateUserEmail(@RequestBody UpdateUserEmailRequest request, @PathVariable Long id) {
        return ok(() -> updateUserEmailPort.update(request),
                "Usuario actualizado correctamente",
                ValidationRequest.of(request),
                ValidStateValidation.of(
                        request.getId().equals(id),
                        "path.id",
                        "Id de la ruta y del cuerpo no coinciden"));

    }
}
