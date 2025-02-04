package com.vet.hc.api.shared.adapter.in.controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.vet.hc.api.shared.application.annotations.RestControllerAdapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * API controller.
 *
 * <p>
 * Controller for general endpoints of the API.
 * </p>
 */
@RestControllerAdapter
@Tag(name = "General endpoints", description = "All of this endpoints are general and don't require authentication.")
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
