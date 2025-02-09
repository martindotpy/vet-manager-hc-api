package com.vet.hc.api.user.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.respondContentResult;
import static com.vet.hc.api.shared.adapter.in.util.ResponseShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vet.hc.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vet.hc.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vet.hc.api.image.core.domain.model.enums.ImageMimeType;
import com.vet.hc.api.shared.application.annotations.RestControllerAdapter;
import com.vet.hc.api.shared.domain.validation.ValidationError;
import com.vet.hc.api.shared.domain.validation.impl.EnumValidation;
import com.vet.hc.api.user.core.adapter.in.request.UpdateUserProfileImageRequest;
import com.vet.hc.api.user.core.adapter.in.response.UserResponse;
import com.vet.hc.api.user.core.application.port.in.UpdateUserProfileImagePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * User image profile controller
 */
@Tag(name = "User Profile Image", description = "User profile image operations")
@RestControllerAdapter
@RequiredArgsConstructor
@RequestMapping("/user/profile-image")
@PreAuthorize("hasRole('ADMIN')")
public class UserProfileImageController {
    private final GetCurrentUserPort getCurrentUserPort;

    private final UpdateUserProfileImagePort updateUserImageProfilePort;

    /**
     * Update current user profile image
     *
     * @param image_file Image file
     * @return Response entity
     */
    @Operation(summary = "Update current user profile image", description = "Update the current user profile image", responses = {
            @ApiResponse(responseCode = "200", description = "User profile image updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "422", description = "Validation error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) }),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)) })
    })
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCurrentUser(
            @RequestParam MultipartFile image_file) {
        var validationErrors = new CopyOnWriteArrayList<ValidationError>();

        validationErrors.addAll(
                validate(EnumValidation.of(
                        ImageMimeType.class,
                        image_file.getContentType(),
                        (ignored) -> ImageMimeType.fromValue(image_file.getContentType()).getValue(),
                        "param.image_file")));

        if (!validationErrors.isEmpty()) {
            return toDetailedFailureResponse(validationErrors);
        }

        UpdateUserProfileImageRequest request;

        try {
            request = UpdateUserProfileImageRequest.builder()
                    .userId(getCurrentUserPort.get().getId())
                    .type(ImageMimeType.fromValue(image_file.getContentType()))
                    .data(image_file.getBytes())
                    .build();
        } catch (IOException e) {
            return toFailureResponse(
                    "Unexpected error while reading the image file",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return respondContentResult(
                AuthenticationResponse.class,
                () -> updateUserImageProfilePort.updateCurrentUser(request),
                "Image del perfil de usuario ha sido actualizado correctamente");
    }

    /**
     * Update user profile image by id
     *
     * @param image_file Image file
     * @param id         User id
     * @return Response entity
     */
    @Operation(summary = "Update user profile image by id", description = "Update the user profile image by id", responses = {
            @ApiResponse(responseCode = "200", description = "User profile image updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "422", description = "Validation error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationError.class)) }),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) })
    })
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateById(
            @RequestParam MultipartFile image_file,
            @RequestParam Long id) {
        var validationErrors = new CopyOnWriteArrayList<ValidationError>();

        validationErrors.addAll(
                validate(EnumValidation.of(ImageMimeType.class, image_file.getContentType(),
                        "param.image_file")));

        if (!validationErrors.isEmpty()) {
            return toDetailedFailureResponse(validationErrors);
        }

        UpdateUserProfileImageRequest request;

        try {
            request = UpdateUserProfileImageRequest.builder()
                    .userId(id)
                    .type(ImageMimeType.fromValue(image_file.getContentType()))
                    .data(image_file.getBytes())
                    .build();
        } catch (IOException e) {
            return toFailureResponse(
                    "Unexpected error while reading the image file",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return respondContentResult(
                UserResponse.class,
                () -> updateUserImageProfilePort.update(request),
                "Image del perfil de usuario ha sido actualizado correctamente");
    }
}
