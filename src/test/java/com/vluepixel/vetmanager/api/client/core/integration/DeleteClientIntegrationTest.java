package com.vluepixel.vetmanager.api.client.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_UPDATE_CLIENT_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the delete client functionality.
 */
public class DeleteClientIntegrationTest extends BaseIntegrationTest {
    private static final String MESSAGE_OK = "Cliente eliminado";
    private static final Function<String, String> MESSAGE_NOT_FOUND = parameter -> String
            .format("Cliente con id %s no encontrado(a)", parameter);
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_DeleteClientWithValidParams_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", VALID_UPDATE_CLIENT_REQUEST.getId()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_DeleteClientWithInvalidParams_ID_NotFound_Forbidden() throws Exception {
        mockMvc.perform(delete("/client/{id}", 10))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_DeleteClientWithInvalidParams_ID_Negative_Forbidden() throws Exception {
        mockMvc.perform(delete("/client/{id}", -10))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_DeleteClientWithInvalidParams_ID_NotNumber_Forbidden() throws Exception {
        mockMvc.perform(delete("/client/{id}", "abcde"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_DeleteClientWithValidParams_Forbidden() throws Exception {
        mockMvc.perform(delete("/user/{id}", VALID_UPDATE_CLIENT_REQUEST.getId())
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_DeleteClientWithInvalidParams_ID_NotFound_Forbidden() throws Exception {
        mockMvc.perform(delete("/client/{id}", 10)
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_DeleteClientWithInvalidParams_ID_Negative_Forbidden() throws Exception {
        mockMvc.perform(delete("/client/{id}", -10)
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_DeleteClientWithInvalidParams_ID_NotNumber_Forbidden() throws Exception {
        mockMvc.perform(delete("/client/{id}", "abcde")
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Role: ADMIN
    @Test
    @DirtiesContext
    void admin_DeleteClientWithValidParams_Ok() throws Exception {
        mockMvc.perform(delete("/client/{id}", VALID_UPDATE_CLIENT_REQUEST.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MESSAGE_OK));
    }

    @Test
    void admin_DeleteClientWithInvalidParams_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(delete("/client/{id}", 10)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND.apply("10")));
    }

    @Test
    void admin_DeleteClientWithInvalidParams_ID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(delete("/client/{id}", -10)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("query.id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id debe ser mayor a 0"));
    }

    @Test
    void admin_DeleteClientWithInvalidParams_ID_NotNumber_UnprocessableEntity() throws Exception {
        mockMvc.perform(delete("/client/{id}", "abcde")
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("path.id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("Illegal argument: For input string: \"abcde\""));
    }
}
