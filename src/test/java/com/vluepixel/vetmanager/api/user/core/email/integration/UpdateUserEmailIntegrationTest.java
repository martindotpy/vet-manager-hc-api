package com.vluepixel.vetmanager.api.user.core.email.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.USER_DTO;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_EMAIL_ALREADY_IN_USE_UPDATE_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_EMAIL_BLANK_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_EMAIL_INVALID_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_EMAIL_ITS_SAME_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_EMAIL_NULL_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_EMAIL_TOO_LONG_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_UPDATE_USER_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.VALID_UPDATE_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.VALID_UPDATE_USER_EMAIL_REQUEST;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Function;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the update user email functionality.
 */
public class UpdateUserEmailIntegrationTest extends BaseIntegrationTest {
    private static final String MESSAGE_OK = "Usuario actualizado correctamente";
    private static final Function<String, String> MESSAGE_NOT_FOUND = parameter -> String
            .format("Usuario con id %s no encontrado(a)", parameter);
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // /user/email
    @Test
    void noUser_UpdateEmailCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // /user/{id}/email
    @Test
    void noUser_UpdateEmailUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateEmailCurrentUserWithAnotherWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Email
    @Test
    void noUser_UpdateEmailCurrentUserWithInvalidArguments_Email_AlreadyInUse_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_ALREADY_IN_USE_UPDATE_ADMIN_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateEmailOtherUser_ID_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateEmailCurrentUserWithInvalidArguments_Email_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_TOO_LONG_ADMIN_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateEmailCurrentUserWithInvalidArguments_Email_Invalid_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_INVALID_ADMIN_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateEmailCurrentUserWithInvalidArguments_Email_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_BLANK_ADMIN_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateEmailCurrentUserWithInvalidArguments_Email_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_NULL_ADMIN_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // /user/{id}/email
    @Test
    void noUser_UpdateEmailOtherUserID_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateEmailOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    // /user/email
    @Test
    void user_UpdateEmailCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // /user/{id}/email
    @Test
    void user_UpdateEmailOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailCurrentUserWithAnotherWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Email
    @Test
    void user_UpdateEmailCurrentUserWithInvalidArguments_Email_AlreadyInUse_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_ALREADY_IN_USE_UPDATE_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailOtherUser_ID_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailCurrentUserWithInvalidArguments_Email_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_TOO_LONG_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailCurrentUserWithInvalidArguments_Email_Invalid_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_INVALID_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailCurrentUserWithInvalidArguments_Email_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_BLANK_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailCurrentUserWithInvalidArguments_Email_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_NULL_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // /user/{id}/email
    @Test
    void user_UpdateEmailOtherUserID_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateEmailOtherUserEmail_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Role: ADMIN
    // /user/email
    @Test
    @Order(1)
    @DirtiesContext
    void admin_UpdateEmailCurrentUserWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateEmailCurrentUserWithAnotherWithValidArguments_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("path.id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    // Email
    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_AlreadyInUse_Conflict() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_ALREADY_IN_USE_UPDATE_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateEmailOtherUser_ID_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("path.id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    @Test
    @Order(2)
    @DirtiesContext
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_ItsSame_Ok() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_ITS_SAME_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_TOO_LONG_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_email"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El correo debe ser válido",
                                        "El nuevo correo no puede tener más de 254 caracteres")));
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_INVALID_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_email"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo debe ser válido"));
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_BLANK_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_email"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo debe ser válido"));
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_NULL_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("new_email"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El nuevo correo es requerido"));
    }

    // /user/{id}/email
    @Test
    void admin_UpdateEmailOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void admin_UpdateEmailOtherUser_NotFound() throws Exception {
        mockMvc.perform(put("/user/{id}/email", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND.apply("10")));
    }

    @Test
    @Order(3)
    @DirtiesContext
    void admin_UpdateEmailOtherUserWithValidsParams() throws Exception {
        mockMvc.perform(put("/user/{id}/email", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(VALID_UPDATE_USER_EMAIL_REQUEST.getId()),
                        jsonPath("$.content.first_name").value(USER_DTO.getFirstName()),
                        jsonPath("$.content.last_name").value(USER_DTO.getLastName()),
                        jsonPath("$.content.roles").isArray(),
                        jsonPath("$.content.roles").value(USER_DTO.getRoles().toArray()[0].toString()),
                        jsonPath("$.content.profile_image_url").value(USER_DTO.getProfileImageUrl()));
    }
}
