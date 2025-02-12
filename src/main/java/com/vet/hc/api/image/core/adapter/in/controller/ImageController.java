package com.vet.hc.api.image.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseShortcuts.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vet.hc.api.image.core.application.port.in.FindImagePort;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.annotations.RestControllerAdapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Image controller.
 */
@Tag(name = "Image")
@RestControllerAdapter
@RequestMapping("/image")
@RequiredArgsConstructor
public final class ImageController {
    private final FindImagePort findImagePort;

    /**
     * Get image by name.
     *
     * @param name Image name
     * @return Image
     */
    @Operation(summary = "Get image by name", description = "Get image by name", responses = {
            @ApiResponse(responseCode = "200", description = "Image found", content = @Content(schema = @Schema(implementation = byte[].class))),
            @ApiResponse(responseCode = "404", description = "Image not found", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        String extension = name.substring(name.lastIndexOf('.') + 1);

        return ok(
                (output) -> findImagePort.findByName(name, output),
                "Image " + name,
                "image/" + extension,
                "Error al encontrar la imagen");
    }
}
