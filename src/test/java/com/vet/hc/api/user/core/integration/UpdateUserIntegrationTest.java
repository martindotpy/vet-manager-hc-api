package com.vet.hc.api.user.core.integration;

import static com.vet.hc.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.USER_DTO;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.BLANK_FIRST_NAME_UPDATE_USER_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.BLANK_LAST_NAME_UPDATE_USER_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.NULL_FIRST_NAME_UPDATE_USER_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.NULL_LAST_NAME_UPDATE_USER_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.TOO_LONG_FIRST_NAME_UPDATE_USER_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.TOO_LONG_LAST_NAME_UPDATE_USER_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_ADMIN_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_USER_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.vet.hc.api.base.BaseIntegrationTest;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse.Detail;

/**
 * Integration tests for the update user functionality.
 *
 * <p>
 * Only the admin user can update other users.
 * </p>
 */
class UpdateUserIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_UpdateUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_UpdateCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // - Role: USER
    @Test
    void user_UpdateOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/" + ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_UpdateCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_UpdateCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // - Role: ADMIN
    @Test
    void admin_UpdateOtherUser_Success() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());
    }

    @Test
    void admin_UpdateCurrentUserAsOtherUser_Success() throws Exception {
        mockMvc.perform(put("/user/" + ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_UpdateCurrentUser_Success() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Validations:
    // The validations in this case only apply to the admin user
    // -----------------------------------------------------------------------------------------------------------------

    // - First name
    @Test
    void admin_UpdateUserWithBlankFirstName_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_FIRST_NAME_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        DetailedFailureResponse.builder()
                                .message("Validation failed")
                                .details(List.of(
                                        new Detail("firstName", List.of("First name is required"))))
                                .build())));
    }

    @Test
    void admin_UpdateUserWithNullFirstName_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_FIRST_NAME_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        DetailedFailureResponse.builder()
                                .message("Validation failed")
                                .details(List.of(
                                        new Detail("firstName", List.of("First name is required"))))
                                .build())));
    }

    @Test
    void admin_UpdateUserWithTooLongFirstName_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TOO_LONG_FIRST_NAME_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        DetailedFailureResponse.builder()
                                .message("Validation failed")
                                .details(List.of(
                                        new Detail("firstName", List.of("First name is too long"))))
                                .build())));
    }

    // - Last name
    @Test
    void admin_UpdateUserWithBlankLastName_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BLANK_LAST_NAME_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        DetailedFailureResponse.builder()
                                .message("Validation failed")
                                .details(List.of(
                                        new Detail("lastName", List.of("Last name is required"))))
                                .build())));
    }

    @Test
    void admin_UpdateUserWithNullLastName_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NULL_LAST_NAME_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        DetailedFailureResponse.builder()
                                .message("Validation failed")
                                .details(List.of(
                                        new Detail("lastName", List.of("Last name is required"))))
                                .build())));
    }

    @Test
    void admin_UpdateUserWithTooLongLastName_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TOO_LONG_LAST_NAME_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        DetailedFailureResponse.builder()
                                .message("Validation failed")
                                .details(List.of(
                                        new Detail("lastName", List.of("Last name is too long"))))
                                .build())));
    }
}