package com.vet.hc.api.user.core.integration;

import static com.vet.hc.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.USER_DTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.vet.hc.api.base.BaseIntegrationTest;

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
        mockMvc.perform(delete("/user/" + USER_DTO.getId()))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_DeleteOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/" + ADMIN_DTO.getId())
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_DeleteCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/" + USER_DTO.getId())
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    @Test
    void admin_DeleteOtherUser_Ok() throws Exception {
        mockMvc.perform(delete("/user/" + USER_DTO.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/" + ADMIN_DTO.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden());
    }
}
