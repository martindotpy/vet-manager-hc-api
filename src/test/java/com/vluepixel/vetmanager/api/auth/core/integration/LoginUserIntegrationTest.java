package com.vluepixel.vetmanager.api.auth.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.BLANK_EMAIL_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.BLANK_PASSWORD_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.INVALID_EMAIL_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.NULL_EMAIL_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.NULL_PASSWORD_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.VALID_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.VALID_LOGIN_USER_RESPONSE;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.WRONG_EMAIL_LOGIN_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.WRONG_PASSWORD_LOGIN_USER_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    void noUser_LoginWithInvalidCredentialsWrongEmail_Unauthorized() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(WRONG_EMAIL_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void noUser_LoginWithInvalidCredentialsWrongPassword_Unauthorized() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(WRONG_PASSWORD_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnauthorized());
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
                .andExpect(status().isConflict());
    }

    @Test
    void user_LoginWithInvalidCredentialsWrongEmail_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(WRONG_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void user_LoginWithInvalidCredentialsWrongPassword_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(WRONG_PASSWORD_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isConflict());
    }

    // - Role: ADMIN
    @Test
    void admin_LoginWithValidCredentials_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void admin_LoginWithInvalidCredentialsWrongPasswornd_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(WRONG_PASSWORD_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void admin_LoginWithInvalidCredentialsWrongEmail_Conflict() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(WRONG_EMAIL_LOGIN_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Validations:
    // The validations apply to all requests, regardless of the role.
    // -----------------------------------------------------------------------------------------------------------------

    // - Email
    @Test
    void all_LoginWithNullEmail_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_EMAIL_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void all_LoginWithBlankEmail_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_EMAIL_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void all_LoginWithInvalidEmail_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }

    // - Password
    @Test
    void all_LoginWithNullPassword_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_PASSWORD_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void all_LoginWithBlankPassword_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_PASSWORD_LOGIN_USER_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }
}
