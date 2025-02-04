package com.vet.hc.api.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vet.hc.api.VetManagerHCApiApplication;
import com.vet.hc.api.base.BaseIntegrationTest.BaseIntegrationTestConfiguration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Abstract base class for integration tests in the Vet Manager HC application.
 *
 * <p>
 * This class provides a consolidated configuration for integration tests by
 * combining several key Spring Boot annotations:
 * </p>
 *
 * <ul>
 * <li>
 * {@link SpringBootTest}: Boots up the complete Spring context, loading all
 * necessary components of the application.
 * </li>
 * <li>
 * {@link ActiveProfiles}: Activates the "test" profile to load test-specific
 * configurations.
 * </li>
 * <li>
 * {@link AutoConfigureMockMvc}: Automatically sets up a {@link MockMvc}
 * instance, allowing for HTTP request simulation without the need to deploy a
 * full web server.
 * </li>
 * </ul>
 *
 * <p>
 * Additionally, this class includes a nested {@link TestConfiguration}
 * class, {@link BaseIntegrationTestConfiguration}, which contributes a
 * Jakarta {@link Validator} bean to the application context. This bean is
 * essential for validating objects during integration tests and preventing
 * unexpected exceptions when creating Hibernate validation beans after cleaning
 * the application context.
 * </p>
 *
 * <p>
 * By extending this class, tests inherit the pre-configured environment needed
 * to interact with the Spring context and perform HTTP-based integration tests
 * efficiently.
 * </p>
 *
 * <p>
 * <strong>Usage example:</strong>
 *
 * <pre>
 * public class SomeIntegrationTest extends BaseIntegrationTest {
 *     // Your test methods can use the injected 'mockMvc' instance to perform HTTP
 *     // requests.
 * }
 * </pre>
 * </p>
 *
 * <p>
 * The structure of the test methods should follow the pattern of the
 * {@link BaseIntegrationTest} class, which separates the tests into two main
 * categories:
 * <ul>
 * <li>Tests without authentication (no Authorization header)</li>
 * <li>Tests with authentication (with Authorization header)</li>
 * </ul>
 *
 * Also, the method pattern name should follow the convention:
 * <code>[Role]_[Action]_[ExpectedResult]</code>
 * </p>
 *
 * @see SpringBootTest
 * @see ActiveProfiles
 * @see AutoConfigureMockMvc
 * @see DirtiesContext
 * @see BaseIntegrationTestConfiguration
 */
@SpringBootTest(classes = { VetManagerHCApiApplication.class, BaseIntegrationTestConfiguration.class })
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseIntegrationTest {
    /**
     * The {@link MockMvc} instance injected to simulate HTTP requests within
     * integration tests.
     */
    @Autowired
    protected MockMvc mockMvc;
    /**
     * The {@link ObjectMapper} instance injected to serialize and deserialize JSON
     * objects.
     */
    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * Configuration class for the base integration test.
     */
    @TestConfiguration
    static class BaseIntegrationTestConfiguration {
        @Bean
        Validator validator() {
            return Validation.buildDefaultValidatorFactory().getValidator();
        }
    }
}
