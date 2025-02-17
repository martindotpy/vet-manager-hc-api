package com.vluepixel.vetmanager.api.user.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.USER_DTO;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_BLANK_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_BLANK_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_EMPTY_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_EMPTY_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_NULL_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_NULL_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_TOO_LONG_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_TOO_LONG_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_BLANK_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_BLANK_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_EMPTY_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_EMPTY_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_NULL_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_NULL_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_TOO_LONG_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_TOO_LONG_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.VALID_FIRSTNAME_MAX_LENGTH_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.VALID_FIRSTNAME_MAX_LENGTH_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.VALID_LASTNAME_MAX_LENGTH_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.VALID_LASTNAME_MAX_LENGTH_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_USER_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the update user functionality.
 *
 * <p>
 * Only the admin user can update other users.
 * </p>
 */
class UpdateUserIntegrationTest extends BaseIntegrationTest {
    private static final String MESSAGE_OK = "Usuario actualizado correctamente";
    private static final Function<String, String> MESSAGE_NOT_FOUND = parameter -> String
            .format("Usuario con id %s no encontrado(a)", parameter);
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // /user
    @Test
    void noUser_UpdateCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateCurrentUserWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // /user/{id}
    @Test
    void noUser_UpdateUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    // /user
    @Test
    void user_UpdateCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateCurrentUserWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // /user/{id}
    @Test
    void user_UpdateOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/" + ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void user_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Role: ADMIN
    // /user
    @Test
    @DirtiesContext
    void admin_UpdateCurrentUserWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnother_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
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

    // - Invalid Arguments
    // FirstName
    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_TooLong_UnprocessableEntity()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es muy largo"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithValidArgument_FirstName_MaxLength_UnprocessableEntity()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
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
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    // LastName
    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOO_LONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es muy largo"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    @Test
    void admin_UpdateCurrentUserWithWithAnotherValidArgument_LastName_MaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
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
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    @Test
    void admin_UpdateCurrentUserWithWithAnotherInvalidArgument_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherIDWithInvalidArgument_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(2),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"),
                        jsonPath("$.details[1].field").value("path.id"),
                        jsonPath("$.details[1].messages.length()").value(1),
                        jsonPath("$.details[1].messages").value("Id del usuario y del cuerpo no coinciden"));
    }

    // - Invalid arguments

    // FirstName
    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es muy largo"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateCurrentUserWithValidArgument_FirstName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    // LastName
    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOO_LONG_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es muy largo"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateCurrentUserWithValidArgument_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    // /user/{id}
    @Test
    void admin_UpdateOtherUser_Success() throws Exception {
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(USER_DTO.getId()),
                        jsonPath("$.content.first_name").value(VALID_UPDATE_USER_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_UPDATE_USER_REQUEST.getLastName()),
                        jsonPath("$.content.email").value(USER_DTO.getEmail()),
                        jsonPath("$.content.roles").isArray(),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.roles").value("USER"),
                        jsonPath("$.content.profile_image_url").isEmpty());

    }

    @Test
    void admin_UpdateCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // - Invalid arguments

    // ID
    @Test
    void admin_UpdateOtherUserWithInvalidArgument_ID_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", "invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("path.id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("Illegal argument: For input string: \"invalid\""));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_ID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", -1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("path.id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("Id de la ruta y del cuerpo no coinciden"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/user/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND.apply("10")));
    }

    // - Invalid arguments

    // FirstName
    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es muy largo"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateOtherUserWithValidArgument_FirstName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(USER_DTO.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_USER_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_USER_REQUEST.getLastName()),
                        jsonPath("$.content.email").value(USER_DTO.getEmail()),
                        jsonPath("$.content.roles").isArray(),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.roles").value("USER"),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    // LastName
    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOO_LONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es muy largo"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateOtherUserWithValidArgument_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(USER_DTO.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_LASTNAME_MAX_LENGTH_UPDATE_USER_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_LASTNAME_MAX_LENGTH_UPDATE_USER_REQUEST.getLastName()),
                        jsonPath("$.content.email").value(USER_DTO.getEmail()),
                        jsonPath("$.content.roles").isArray(),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.roles").value("USER"),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }
}