package com.vluepixel.vetmanager.api.appointment.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the get appointment use case.
 */
public class GetAppointmentIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER

    // Role: ADMIN
    @Test
    void admin_GetAppointmentWithValidParams_Ok() throws Exception {
        MultiValueMap<String, String> queryparams = new LinkedMultiValueMap<>();
        queryparams.add("page", "1");
        queryparams.add("size", "10");
        queryparams.add("order", "asc");
        queryparams.add("order_by", "description");

        mockMvc.perform(get("/appointment")
                .queryParams(queryparams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }
}
