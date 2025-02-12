package com.vluepixel.vetmanager.api.auth.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.BLANK_EMAIL_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.BLANK_PASSWORD_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.EMAIL_WRONG_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.INVALID_EMAIL_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.NULL_EMAIL_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.NULL_PASSWORD_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.PASSWORD_WRONG_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.VALID_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.VALID_LOGIN_USER_RESPONSE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the login user use case.
 */
class LoginUserIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_LoginWithValidCredentials_Ok() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LOGIN_USER_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(VALID_LOGIN_USER_RESPONSE)));
    }

    // Email
    @Test
    void noUser_LoginWithInvalidCredentials_Email_Wrong_Unauthorized() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EMAIL_WRONG_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void noUser_LoginWithInvalidCredentials_Email_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details").isArray());
    }

    @Test
    void noUser_LoginWithInvalidCredentials_Email_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_EMAIL_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details").isArray());
    }

    @Test
    void noUser_LoginWithInvalidCredentials_Email_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_EMAIL_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details").isArray());
    }

    // Password
    @Test
    void noUser_LoginWithInvalidCredentials_Password_Wrong_Unauthorized() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSWORD_WRONG_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void noUser_LoginWithInvalidCredentials_Password_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_PASSWORD_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details").isArray());
    }

    @Test
    void noUser_LoginWithInvalidCredentials_Password_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_PASSWORD_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.details").isArray());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // - Role: USER
    @Test
    void user_LoginWithValidCredentials_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    // Email
    @Test
    void user_LoginWithInvalidCredentials_Email_Wrong_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EMAIL_WRONG_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void user_LoginWithInvalidCredentials_Email_Invalid_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void user_LoginWithInvalidCredentials_Email_Blank_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void user_LoginWithInvalidCredentials_Email_Null_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    // Password
    @Test
    void user_LoginWithInvalidCredentials_Password_Wrong_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSWORD_WRONG_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void user_LoginWithInvalidCredentials_Password_Blank_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_PASSWORD_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void user_LoginWithInvalidCredentials_Password_Null_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_PASSWORD_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    // - Role: ADMIN
    @Test
    void admin_LoginWithValidCredentials_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    // Email
    @Test
    void admin_LoginWithInvalidCredentials_Email_Wrong_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EMAIL_WRONG_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_LoginWithInvalidCredentials_Email_Invalid_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_LoginWithInvalidCredentials_Email_Blank_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_LoginWithInvalidCredentials_Email_Null_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    // Password
    @Test
    void admin_LoginWithInvalidCredentials_Password_Wrong_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSWORD_WRONG_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_LoginWithInvalidCredentials_Password_Blank_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_PASSWORD_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_LoginWithInvalidCredentials_Password_Null_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_PASSWORD_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").isString());
    }
}
