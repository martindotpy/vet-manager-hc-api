package com.vet.hc.api.user.core.integration;

import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.vet.hc.api.base.BaseIntegrationTest;

class FindUserIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_FindUsers_Forbidden() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_FindUsers_Forbidden() throws Exception {
        mockMvc.perform(get("/user")
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    @Test
    void admin_FindUsers_Ok() throws Exception {
        mockMvc.perform(get("/user")
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

    @Test
    void admin_FindUsersByFields_Ok() throws Exception {
        mockMvc.perform(get("/user")
                .header("Authorization", BEARER_ADMIN_JWT)
                .param("id", "1")
                .param("first_name", "admin")
                .param("last_name", "admin")
                .param("email", "admin")
                .param("role", "admin"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1));
    }
}
