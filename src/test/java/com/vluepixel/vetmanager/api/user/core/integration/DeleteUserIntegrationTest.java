package com.vluepixel.vetmanager.api.user.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.USER_DTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Function;

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
    private static final String MESSAGE_FORBIDDEN_USER = "El usuario no se puede borrar así mismo";
    private static final String MESSAGE_OK = "Usuario eliminado correctamente";
    private static final Function<String, String> MESSAGE_NOT_FOUND = user -> String
            .format("Usuario con id %s no encontrado(a)", user);
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_DeleteOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", USER_DTO.getId()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_DeleteCurrentUserAsOtherUserWithInvalidArgument_ID_Invalid_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", "abcde"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_DeleteOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", ADMIN_DTO.getId())
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_DeleteCurrentUserAsOtherUserWithInvalidArgument_ID_Invalid_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", USER_DTO.getId())
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_DeleteCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", "abcde")
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Role: ADMIN
    @Test
    @DirtiesContext
    void admin_DeleteOtherUser_Ok() throws Exception {
        mockMvc.perform(delete("/user/{id}", USER_DTO.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MESSAGE_OK));
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", ADMIN_DTO.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN_USER));
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_NotFound() throws Exception {
        mockMvc.perform(delete("/user/{id}", 10)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(MESSAGE_NOT_FOUND.apply("10")));
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_Negative() throws Exception {
        mockMvc.perform(delete("/user/{id}", -10)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_DeleteCurrentUserAsOtherUser_UnprocessableEntity() throws Exception {
        mockMvc.perform(delete("/user/{id}", "abcde")
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }
}
