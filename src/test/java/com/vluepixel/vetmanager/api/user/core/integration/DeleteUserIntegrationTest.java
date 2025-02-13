package com.vluepixel.vetmanager.api.user.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.USER_DTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the delete user functionality.
 *
 * <p>
 * Only the admin users can delete users.
 * </p>
 */
class DeleteUserIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_DeleteOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", USER_DTO.getId()))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_DeleteOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", ADMIN_DTO.getId())
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_DeleteCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", USER_DTO.getId())
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    @Test
    @DirtiesContext
    void admin_DeleteOtherUser_Ok() throws Exception {
        mockMvc.perform(delete("/user/{od}", USER_DTO.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @DirtiesContext
    void admin_DeleteCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", ADMIN_DTO.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_NotFound() throws Exception {
        mockMvc.perform(delete("/user/{id}", 10)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound());
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_Negative() throws Exception {
        mockMvc.perform(delete("/user/{id}", -10)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound());
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_UnprocessableEntity() throws Exception {
        mockMvc.perform(delete("/user/{id}", "abcde")
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }
}
