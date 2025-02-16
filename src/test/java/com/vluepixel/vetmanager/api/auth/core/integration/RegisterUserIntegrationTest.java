package com.vluepixel.vetmanager.api.auth.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_NULL_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_EMAIL_TOOLONG_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_BLANK_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_EMPTY_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_NULL_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_TOOLONG_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_BLANK_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_EMPTY_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_NULL_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_TOOLONG_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_PASSWORD_BLANK_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_PASSWORD_EMPTY_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_PASSWORD_NULL_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_PASSWORD_TOOLONG_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_PASSWORD_TOOSHORT_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_RESPONSE_CONTENT;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_RESPONSE_CONTENT;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_PASSWORD_MAX_LENGTH_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_PASSWORD_MIN_LENGTH_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_REGISTER_USER_RESPONSE_CONTENT;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
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
    void noUser_RegisterUserWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // - Invalid arguments.
    // FirstName
    @Test
    void noUser_RegisterUserWithInvalidArgument_FirsName_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithValidArgument_FirsName_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_FirsName_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_FirsName_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_FirsName_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // LastName
    @Test
    void noUser_RegisterUserWithInvalidArgument_LastName_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOOLONG_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithValidArgument_LastName_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_LastName_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_LastName_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_LastName_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // Email
    @Test
    @DirtiesContext
    void noUser_RegisterUserWithInvalidArgument_Email_AlreadyInUse_Forbidden() throws Exception {
        admin_RegisterUserWithValidArguments_Ok();
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void noUser_RegisterUserWithSameEmailOfDeletedUser_ForBidden() throws Exception {
        admin_RegisterUserWithValidArguments_Ok();

        mockMvc.perform(delete("/user/{id}", VALID_REGISTER_USER_RESPONSE_CONTENT.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Email_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_TOOLONG_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Email_Invalid_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Email_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Email_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Email_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_NULL_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // Password
    @Test
    void noUser_RegisterUserWithInvalidArgument_Password_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_TOOLONG_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithValidArgument_Password_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PASSWORD_MAX_LENGTH_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Password_TooShort_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_TOOSHORT_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithValidArgument_Password_MinLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PASSWORD_MIN_LENGTH_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Password_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_BLANK_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Password_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_EMPTY_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_RegisterUserWithInvalidArgument_Password_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_NULL_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_RegisterUserWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // - Invalid arguments.

    // FirstName
    @Test
    void user_RegisterUserWithInvalidArgument_FirsName_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithValidArgument_FirsName_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_FirsName_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_FirsName_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_FirsName_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // LastName
    @Test
    void user_RegisterUserWithInvalidArgument_LastName_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithValidArgument_LastName_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_LastName_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_LastName_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_LastName_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Email
    @Test
    @DirtiesContext
    void user_RegisterUserWithInvalidArgument_Email_AlreadyInUse_Forbidden() throws Exception {
        admin_RegisterUserWithValidArguments_Ok();
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void user_RegisterUserWithSameEmailOfDeletedUser_ForBidden() throws Exception {
        admin_RegisterUserWithValidArguments_Ok();

        mockMvc.perform(delete("/user/{id}", VALID_REGISTER_USER_RESPONSE_CONTENT.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());

        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_USER_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Email_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Email_Invalid_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Email_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Email_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Email_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Password
    @Test
    void user_RegisterUserWithInvalidArgument_Password_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithValidArgument_Password_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PASSWORD_MAX_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Password_TooShort_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_TOOSHORT_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithValidArgument_Password_MinLength_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PASSWORD_MIN_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Password_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Password_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_RegisterUserWithInvalidArgument_Password_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    @Test
    @Order(1)
    @DirtiesContext
    void admin_RegisterUserWithValidArguments_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()),
                        jsonPath("$.content.first_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getLastName()),
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()),
                        jsonPath("$.content.roles[0]").value("USER"),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    // - Invalid arguments.

    // FirstName
    @Test
    void admin_RegisterUserWithInvalidArgument_FirsName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El nombre no debe de tener más de 50 caracteres"));
    }

    @Test
    @Order(2)
    @DirtiesContext
    void admin_RegisterUserWithValidArgument_FirsName_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_RESPONSE_CONTENT.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getLastName()),
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()),
                        jsonPath("$.content.roles[0]").value("USER"),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_FirsName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El nombre es requerido"));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_FirsName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El nombre es requerido"));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_FirsName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El nombre es requerido"));
    }

    // LastName
    @Test
    void admin_RegisterUserWithInvalidArgument_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("El apellido no debe de tener más de 50 caracteres"));
    }

    @Test
    @Order(3)
    @DirtiesContext
    void admin_RegisterUserWithValidArgument_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()),
                        jsonPath("$.content.first_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_RESPONSE_CONTENT.getLastName()),
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()),
                        jsonPath("$.content.roles[0]").value("USER"),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El apellido es requerido"));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El apellido es requerido"));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El apellido es requerido"));
    }

    // Email
    @Test
    @Order(4)
    @DirtiesContext
    void admin_RegisterUserWithInvalidArgument_Email_AlreadyInUse_Conflict() throws Exception {
        admin_RegisterUserWithValidArguments_Ok();
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @Order(5)
    @DirtiesContext
    void admin_RegisterUserWithSameEmailOfDeletedUser_Ok() throws Exception {
        admin_RegisterUserWithValidArguments_Ok();

        mockMvc.perform(delete("/user/{id}", VALID_REGISTER_USER_RESPONSE_CONTENT.getId())
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());

        mockMvc.perform(post("/auth/register")
                .header("Authorization", BEARER_ADMIN_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_REGISTER_USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()),
                        jsonPath("$.content.first_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getLastName()),
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()),
                        jsonPath("$.content.roles[0]").value("USER"),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Email_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("email"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El correo es inválido"));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Email_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_INVALID_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("email"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El correo es inválido"));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Email_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("email"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El correo es requerido", "El correo es inválido")));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Email_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("email"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El correo es requerido", "El correo es inválido")));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Email_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("email"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El correo es requerido"));
    }

    // Password
    @Test
    void admin_RegisterUserWithInvalidArgument_Password_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_TOOLONG_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("La contraseña debe tener entre 8 y 60 caracteres"));
    }

    @Test
    @Order(6)
    @DirtiesContext
    void admin_RegisterUserWithValidArgument_Password_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PASSWORD_MAX_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()),
                        jsonPath("$.content.first_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getLastName()),
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()),
                        jsonPath("$.content.roles[0]").value("USER"),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Password_TooShort_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_TOOSHORT_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("La contraseña debe tener entre 8 y 60 caracteres"));
    }

    @Test
    @Order(7)
    @DirtiesContext
    void admin_RegisterUserWithValidArgument_Password_MinLength_Ok() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PASSWORD_MIN_LENGTH_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content.id").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getId()),
                        jsonPath("$.content.first_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getLastName()),
                        jsonPath("$.content.email").value(VALID_REGISTER_USER_RESPONSE_CONTENT.getEmail()),
                        jsonPath("$.content.roles[0]").value("USER"),
                        jsonPath("$.content.roles.length()").value(1),
                        jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Password_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_BLANK_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages").value(containsInAnyOrder("La contraseña es requerida",
                                "La contraseña debe tener entre 8 y 60 caracteres")));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Password_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_EMPTY_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("La contraseña debe tener entre 8 y 60 caracteres",
                                        "La contraseña es requerida")));
    }

    @Test
    void admin_RegisterUserWithInvalidArgument_Password_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PASSWORD_NULL_REGISTER_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("password"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("La contraseña es requerida"));
    }
}