package com.vet.hc.api.auth.core.integration;

import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vet.hc.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_REQUEST;
import static com.vet.hc.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_RESPONSE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vet.hc.api.base.BaseIntegrationTest;

/**
 * Integration tests for the register user use case.
 *
 * <p>
 * Only admin users can register new users.
 * </p>
 */
class RegisterUserIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_RegisterUserWithValidRegister_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // - Role: USER
    @Test
    void user_RegisterUserWithValidRegister_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_USER_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // - Role: ADMIN
    @Test
    void admin_RegisterUserWithValidRegister_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VALID_REGISTER_USER_RESPONSE)));
    }

    @Test
    @Order(1)
    @DirtiesContext
    void admin_RegisterUserWithEmailAlreadyInUse_Conflict() throws Exception {
        admin_RegisterUserWithValidRegister_Ok();

        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(2)
    @DirtiesContext
    void admin_RegisterUserWithSameEmailOfDeletedUser_Ok() throws Exception {
        admin_RegisterUserWithValidRegister_Ok();

        mockMvc.perform(delete("/user/{id}", VALID_REGISTER_USER_RESPONSE.getContent().getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());

        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VALID_REGISTER_USER_RESPONSE)));
    }
}
