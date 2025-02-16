package com.vluepixel.vetmanager.api.auth.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_NEWPASSWORD_BLANK_UPDATE_ADMIN_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_NEWPASSWORD_EMPTY_UPDATE_ADMIN_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_NEWPASSWORD_NULL_UPDATE_ADMIN_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_NEWPASSWORD_TOO_LONG_UPDATE_ADMIN_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_PASSWORD_BLANK_UPDATE_USER_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_PASSWORD_EMPTY_UPDATE_USER_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_PASSWORD_NULL_UPDATE_USER_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.INVALID_PASSWORD_WRONG_UPDATE_USER_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.VALID_UPDATE_ADMIN_PASSWORD_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.UpdatePasswordUserDataProvider.VALID_UPDATE_USER_PASSWORD_REQUEST;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the update password user use case.
 */
public class UpdatePasswordIntegrationTest extends BaseIntegrationTest {
    private static final String MESSAGE_FORBIDDEN = "Acceso denegado";
    private static final String MESSAGE_OK = "Contraseña actualizada correctamente";
    private static final String MESSAGE_UNAUTHORIZED = "Credenciales inválidas";
    private static final String MESSAGE_UNPROCESSABLE_ENTITY = "Validación fallida";

    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_UpdatePasswordWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // - Invalid Arguments
    // Password
    @Test
    void noUser_UpdatePasswordWithInvalidArgument_Password_Wrong_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_WRONG_UPDATE_USER_PASSWORD_REQUEST)))
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdatePasswordWithInvalidArgument_Password_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_BLANK_UPDATE_USER_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdatePasswordWithInvalidArgument_Password_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_EMPTY_UPDATE_USER_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdatePasswordWithInvalidArgument_Password_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_NULL_UPDATE_USER_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // NewPassword
    @Test
    void noUser_UpdatePasswordWithInvalidArgument_NewPassword_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_TOO_LONG_UPDATE_ADMIN_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdatePasswordWithInvalidArgument_NewPassword_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_BLANK_UPDATE_ADMIN_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdatePasswordWithInvalidArgument_NewPassword_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_EMPTY_UPDATE_ADMIN_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
        ;
    }

    @Test
    void noUser_UpdatePasswordWithInvalidArgument_NewPassword_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_NULL_UPDATE_ADMIN_PASSWORD_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // - Role: USER
    @Test
    @Order(1)
    @DirtiesContext
    void user_UpdatePasswordWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MESSAGE_OK));
    }

    // - Invalid Arguments
    // Password
    @Test
    void user_UpdatePasswordWithInvalidArgument_Password_Wrong_Unauthorized() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_WRONG_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(MESSAGE_UNAUTHORIZED));
    }

    @Test
    void user_UpdatePasswordWithInvalidArgument_Password_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_BLANK_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("La contraseña es requerida"));
    }

    @Test
    void user_UpdatePasswordWithInvalidArgument_Password_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_EMPTY_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages").value(
                                containsInAnyOrder("La contraseña es requerida",
                                        "La contraseña debe tener entre 8 y 60 caracteres")));
    }

    @Test
    void user_UpdatePasswordWithInvalidArgument_Password_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_NULL_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("La contraseña es requerida"));
    }

    // NewPassword
    @Test
    void user_UpdatePasswordWithInvalidArgument_NewPassword_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_TOO_LONG_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La nueva contraseña debe tener entre 8 y 60 caracteres"));
    }

    @Test
    void user_UpdatePasswordWithInvalidArgument_NewPassword_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_BLANK_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("La nueva contraseña debe tener entre 8 y 60 caracteres",
                                        "La nueva contraseña es requerida")));
    }

    @Test
    void user_UpdatePasswordWithInvalidArgument_NewPassword_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_EMPTY_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("La nueva contraseña debe tener entre 8 y 60 caracteres",
                                        "La nueva contraseña es requerida")));
    }

    @Test
    void user_UpdatePasswordWithInvalidArgument_NewPassword_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_NULL_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La nueva contraseña es requerida"));
    }

    // - Role: ADMIN
    @Test
    @Order(2)
    @DirtiesContext
    void admin_UpdatePasswordWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MESSAGE_OK));
    }

    // - Invalid Arguments
    // Password
    @Test
    void admin_UpdatePasswordWithInvalidArgument_Password_Wrong_Unauthorized() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_WRONG_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(MESSAGE_UNAUTHORIZED));
    }

    @Test
    void admin_UpdatePasswordWithInvalidArgument_Password_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_BLANK_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La contraseña es requerida"));
    }

    @Test
    void admin_UpdatePasswordWithInvalidArgument_Password_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_EMPTY_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("La contraseña es requerida",
                                        "La contraseña debe tener entre 8 y 60 caracteres")));
    }

    @Test
    void admin_UpdatePasswordWithInvalidArgument_Password_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_NULL_UPDATE_USER_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("La contraseña es requerida"));
    }

    // NewPassword
    @Test
    void admin_UpdatePasswordWithInvalidArgument_NewPassword_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_TOO_LONG_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La nueva contraseña debe tener entre 8 y 60 caracteres"));
    }

    @Test
    void admin_UpdatePasswordWithInvalidArgument_NewPassword_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_BLANK_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("La nueva contraseña debe tener entre 8 y 60 caracteres",
                                        "La nueva contraseña es requerida")));
    }

    @Test
    void admin_UpdatePasswordWithInvalidArgument_NewPassword_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_EMPTY_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("La nueva contraseña debe tener entre 8 y 60 caracteres",
                                        "La nueva contraseña es requerida")));
    }

    @Test
    void admin_UpdatePasswordWithInvalidArgument_NewPassword_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NEWPASSWORD_NULL_UPDATE_ADMIN_PASSWORD_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La nueva contraseña es requerida"));
    }
}