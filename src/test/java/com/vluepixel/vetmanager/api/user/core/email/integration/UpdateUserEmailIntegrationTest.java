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
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_EMAIL_TOOLONG_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.INVALID_UPDATE_USER_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.VALID_UPDATE_ADMIN_EMAIL_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.email.data.UpdateUserEmailDataProvider.VALID_UPDATE_USER_EMAIL_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the update user email functionality.
 */
public class UpdateUserEmailIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // /user/email
    @Test
    void noUser_UpdateEmailCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // /user/{id}/email
    @Test
    void noUser_UpdateEmailUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST)))
                .andExpect(status().isForbidden());
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
                .andExpect(status().isForbidden());
    }

    // /user/{id}/email
    @Test
    void user_UpdateEmailOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_UpdateEmailCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
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
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @Order(2)
    @DirtiesContext
    void admin_UpdateEmailCurrentUserWithAnotherWithValidArguments_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
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
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_ItsSame_Ok() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_ITS_SAME_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_TOOLONG_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_INVALID_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_BLANK_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateEmailCurrentUserWithInvalidArguments_Email_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAIL_NULL_ADMIN_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // /user/{id}/email
    @Test
    void admin_UpdateEmailOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}/email", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_UpdateEmailOtherUser_NotFound() throws Exception {
        mockMvc.perform(put("/user/{id}/email", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/user/{id}/email", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_EMAIL_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());
    }
}
