package com.vluepixel.vetmanager.api.auth.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_EMAIL_BLANK;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_EMAIL_EMPTY;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_EMAIL_INVALID;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_EMAIL_NULL;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_FIRST_NAME_BLANK;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_FIRST_NAME_EMPTY;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_FIRST_NAME_NULL;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_LAST_NAME_BLANK;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_LAST_NAME_EMPTY;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_LAST_NAME_NULL;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_PASSWORD_BLANK;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_PASSWORD_EMPTY;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_REGISTER_USER_REQUEST_PASSWORD_NULL;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_RESPONSE_CONTENT;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

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

    // Role: USER
    @Test
    void user_RegisterUserWithValidRegister_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_USER_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    @Test
    @DirtiesContext
    void admin_RegisterUserWithValidRegister_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()))
                .andExpect(jsonPath("$.content.first_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getFirstName()))
                .andExpect(jsonPath("$.content.last_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getLastName()))
                .andExpect(jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
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
    @DirtiesContext
    void admin_RegisterUserWithSameEmailOfDeletedUser_Ok() throws Exception {
        admin_RegisterUserWithValidRegister_Ok();

        mockMvc.perform(delete("/user/{id}", VALID_REGISTER_USER_RESPONSE_CONTENT.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());

        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()))
                .andExpect(jsonPath("$.content.first_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getFirstName()))
                .andExpect(jsonPath("$.content.last_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getLastName()))
                .andExpect(jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Validations:
    // The validations apply to all requests, regardless of the role.
    // -----------------------------------------------------------------------------------------------------------------

    // - First name
    @Test
    void admin_RegisterUserWithInvalidFirstNameNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_FIRST_NAME_NULL)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    void admin_RegisterUserWithInvalidFirstNameEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_FIRST_NAME_EMPTY)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    void admin_RegisterUserWithInvalidFirstNameBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_FIRST_NAME_BLANK)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    @DirtiesContext
    void admin_RegisterUserWithValidFirstNameMaxLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.id").value(3L))
                .andExpect(jsonPath("$.content.first_name")
                        .value(VALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH.getFirstName()))
                .andExpect(jsonPath("$.content.last_name")
                        .value(VALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH.getLastName()))
                .andExpect(
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH.getEmail()))
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidFirstNameMaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_FIRST_NAME_MAX_LENGTH)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is too long"));
    }

    // - Last name
    @Test
    void admin_RegisterUserWithInvalidLastNameNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_LAST_NAME_NULL)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    void admin_RegisterUserWithInvalidLastNameEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_LAST_NAME_EMPTY)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    void admin_RegisterUserWithInvalidLastNameBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_LAST_NAME_BLANK)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    @DirtiesContext
    void admin_RegisterUserWithValidLastNameMaxLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.id").value(3L))
                .andExpect(jsonPath("$.content.first_name")
                        .value(VALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH.getFirstName()))
                .andExpect(
                        jsonPath("$.content.last_name")
                                .value(VALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH.getLastName()))
                .andExpect(
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH.getEmail()))
                .andExpect(
                        jsonPath("$.content.roles.length()").value(1))
                .andExpect(
                        jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidLastNameMaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_LAST_NAME_MAX_LENGTH)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is too long"));
    }

    // - Email
    @Test
    void admin_RegisterUserWithInvalidEmailNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_EMAIL_NULL)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("email"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Email is required"));
    }

    @Test
    void admin_RegisterUserWithInvalidEmailEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_EMAIL_EMPTY)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("email"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Email is required"));
    }

    @Test
    void admin_RegisterUserWithInvalidEmailBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_EMAIL_BLANK)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("email"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(2))
                .andExpect(jsonPath("$.details[0].messages")
                        .value(containsInAnyOrder("Email is required", "Email is invalid")));
    }

    @Test
    void admin_RegisterUserWithInvalidEmailInvalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_EMAIL_INVALID)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("email"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Email is invalid"));
    }

    // - Password
    @Test
    void admin_RegisterUserWithInvalidPasswordNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_PASSWORD_NULL)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("password"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Password is required"));
    }

    @Test
    void admin_RegisterUserWithInvalidPasswordEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_PASSWORD_EMPTY)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("password"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(2))
                .andExpect(jsonPath("$.details[0].messages")
                        .value(containsInAnyOrder("Password is required",
                                "Password must be between 8 and 64 characters")));
    }

    @Test
    void admin_RegisterUserWithInvalidPasswordBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_PASSWORD_BLANK)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("password"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(2))
                .andExpect(jsonPath("$.details[0].messages")
                        .value(containsInAnyOrder("Password is required",
                                "Password must be between 8 and 64 characters")));
    }

    @Test
    void admin_RegisterUserWithInvalidPasswordMinLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("password"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Password must be between 8 and 64 characters"));
    }

    @Test
    @DirtiesContext
    void admin_RegisterUserWithValidPasswordMinLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.id").value(3L))
                .andExpect(
                        jsonPath("$.content.first_name")
                                .value(VALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH.getFirstName()))
                .andExpect(
                        jsonPath("$.content.last_name")
                                .value(VALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH.getLastName()))
                .andExpect(
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_REQUEST_PASSWORD_MIN_LENGTH.getEmail()))
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidPasswordMaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("password"))
                .andExpect(jsonPath("$.details[0].messages.length()").value(1))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Password must be between 8 and 64 characters"));
    }

    @Test
    @DirtiesContext
    void admin_RegisterUserWithValidPasswordMaxLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.id").value(3L))
                .andExpect(jsonPath("$.content.first_name")
                        .value(VALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH.getFirstName()))
                .andExpect(jsonPath("$.content.last_name")
                        .value(VALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH.getLastName()))
                .andExpect(jsonPath("$.content.email")
                        .value(VALID_REGISTER_USER_REQUEST_PASSWORD_MAX_LENGTH.getEmail()))
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }
}
