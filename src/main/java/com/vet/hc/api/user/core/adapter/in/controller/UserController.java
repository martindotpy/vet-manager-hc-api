package com.vet.hc.api.user.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.respondContentResult;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.respondVoidResult;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vet.hc.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.annotations.RestControllerAdapter;
import com.vet.hc.api.shared.domain.validation.ValidationPayload;
import com.vet.hc.api.shared.domain.validation.impl.ValidStateValidation;
import com.vet.hc.api.user.core.adapter.in.request.UpdateUserRequest;
import com.vet.hc.api.user.core.adapter.in.response.UserResponse;
import com.vet.hc.api.user.core.application.port.in.DeleteUserPort;
import com.vet.hc.api.user.core.application.port.in.UpdateUserPort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * User controller.
 */
@Tag(name = "User", description = "User")
@RestControllerAdapter
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final GetCurrentUserPort getCurrentUserPort;

    private final UpdateUserPort updateCurrentUserPort;
    private final DeleteUserPort deleteUserPort;

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
        return respondContentResult(
                AuthenticationResponse.class,
                () -> updateCurrentUserPort.updateCurrentUser(request),
                "Usuario actualizado correctamente",
                ValidationPayload.of(request),
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
        return respondContentResult(
                UserResponse.class,
                () -> updateCurrentUserPort.update(request),
                "Usuario actualizado correctamente",
                ValidationPayload.of(request),
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
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Only admin can delete user", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return respondVoidResult(
                () -> deleteUserPort.deleteById(id),
                "Usuario eliminado correctamente");
    }
}
