package com.vluepixel.vetmanager.api.shared.adapter.in.controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.vluepixel.vetmanager.api.shared.application.annotations.RestControllerAdapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API controller.
 *
 * <p>
 * Controller for general endpoints of the API.
 * </p>
 */
@Tag(name = "General endpoints", description = "All of this endpoints are general and don't require authentication.")
@Slf4j
@RestControllerAdapter
@RequiredArgsConstructor
public class ApiController {
    /**
     * Health check.
     *
     * <p>
     * Check if the server is running.
     * </p>
     *
     * @return A string with the message "Ok"
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the server is running.")
    @SecurityRequirements
    public String health() {
        return "Ok";
    }
}
