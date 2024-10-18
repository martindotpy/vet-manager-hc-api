package com.vet.hc.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configures the base path for the REST API.
 */
@ApplicationPath("api/v0")
public class VetManagerHCApiApplication extends Application {
}
