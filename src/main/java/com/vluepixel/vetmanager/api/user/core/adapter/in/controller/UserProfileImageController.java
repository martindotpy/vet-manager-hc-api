package com.vluepixel.vetmanager.api.user.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.error;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;
import static com.vluepixel.vetmanager.api.shared.domain.validation.Validator.validate;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vluepixel.vetmanager.api.auth.core.adapter.in.response.AuthenticationResponse;
import com.vluepixel.vetmanager.api.auth.core.application.port.out.GetCurrentUserPort;
import com.vluepixel.vetmanager.api.image.core.domain.model.enums.ImageMimeType;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.EnumValidation;
import com.vluepixel.vetmanager.api.user.core.adapter.in.response.UserResponse;
import com.vluepixel.vetmanager.api.user.core.application.port.in.UpdateUserProfileImagePort;
import com.vluepixel.vetmanager.api.user.core.domain.request.UpdateUserProfileImageRequest;

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
public class UserProfileImageController {
    private final GetCurrentUserPort getCurrentUserPort;

    private final UpdateUserProfileImagePort updateUserImageProfilePort;

    /**
     * Update current user profile image
     *
     * @param imageFile The image file.
     * @return Response entity
     */
    @Operation(summary = "Update current user profile image", description = "Update the current user profile image", responses = {
            @ApiResponse(responseCode = "200", description = "User profile image updated successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = ValidationError.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
    })
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCurrentUser(
            @RequestParam(name = "image_file") MultipartFile imageFile) {
        validate(EnumValidation.of(
                ImageMimeType.class,
                imageFile.getContentType(),
                (ignored) -> ImageMimeType.fromValue(imageFile.getContentType()).getValue(),
                "param.image_file"));

        UpdateUserProfileImageRequest request;

        try {
            request = UpdateUserProfileImageRequest.builder()
                    .userId(getCurrentUserPort.get().getId())
                    .type(ImageMimeType.fromValue(imageFile.getContentType()))
                    .data(imageFile.getBytes())
                    .build();
        } catch (IOException e) {
            return error("Error inesperado leyendo el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ok(() -> updateUserImageProfilePort.updateCurrentUser(request),
                "Image del perfil de usuario ha sido actualizado correctamente");
    }

    /**
     * Update user profile image by id
     *
     * @param imageFile The image file.
     * @param id        User id
     * @return Response entity
     * @throws IOException If an error occurs while reading the image file
     */
    @Operation(summary = "Update user profile image by id", description = "Update the user profile image by id", responses = {
            @ApiResponse(responseCode = "200", description = "User profile image updated successfully", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = ValidationError.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateById(
            @RequestParam MultipartFile imageFile,
            @RequestParam Long id) throws IOException {
        validate(EnumValidation.of(ImageMimeType.class, imageFile.getContentType(), "param.image_file"));

        UpdateUserProfileImageRequest request = UpdateUserProfileImageRequest.builder()
                .userId(id)
                .type(ImageMimeType.fromValue(imageFile.getContentType()))
                .data(imageFile.getBytes())
                .build();

        return ok(() -> updateUserImageProfilePort.update(request),
                "Image del perfil de usuario ha sido actualizado correctamente");
    }
}
