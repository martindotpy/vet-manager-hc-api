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
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_TOOLONG_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_FIRSTNAME_TOOLONG_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_BLANK_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_BLANK_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_EMPTY_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_EMPTY_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_NULL_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_NULL_UPDATE_USER_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_TOOLONG_UPDATE_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.user.core.data.UpdateUserDataProvider.INVALID_LASTNAME_TOOLONG_UPDATE_USER_REQUEST;
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
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // /user
    @Test
    void noUser_UpdateCurrentUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_UpdateCurrentUserWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // /user/{id}
    @Test
    void noUser_UpdateUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void noUser_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_USER_REQUEST)))
                .andExpect(status().isForbidden());
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
                .andExpect(status().isForbidden());
    }

    @Test
    void user_UpdateCurrentUserWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // /user/{id}
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
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_TooLong_Forbidden()
            throws Exception {
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    // /user
    @Test
    @DirtiesContext
    void admin_UpdateCurrentUserWithValidArguments_Success() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnother_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // - Invalid Arguments
    // FirstName
    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_TooLong_UnprocessableEntity()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithValidArgument_FirstName_MaxLength_UnprocessableEntity()
            throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // LastName
    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOOLONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithWithAnotherValidArgument_LastName_MaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherWithInvalidArgument_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithWithAnotherInvalidArgument_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithAnotherIDWithInvalidArgument_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // - Invalid arguments

    // FirstName
    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithValidArgument_FirstName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // LastName
    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOOLONG_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithValidArgument_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidArgument_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // /user/{id}
    @Test
    void admin_UpdateOtherUser_Success() throws Exception {
        mockMvc.perform(put("/user/{id}", USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.id").value(USER_DTO.getId()))
                .andExpect(jsonPath("$.content.first_name").value(VALID_UPDATE_USER_REQUEST.getFirstName()))
                .andExpect(jsonPath("$.content.last_name").value(VALID_UPDATE_USER_REQUEST.getLastName()))
                .andExpect(jsonPath("$.content.email").value(USER_DTO.getEmail()))
                .andExpect(jsonPath("$.content.roles").isArray())
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());

    }

    @Test
    void admin_UpdateCurrentUserAsOtherUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/{id}", ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden());
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
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_ID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", -1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/user/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isString());
    }

    // - Invalid arguments

    // FirstName
    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithValidArgument_FirstName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // LastName
    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOOLONG_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithValidArgument_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAX_LENGTH_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidArgument_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }
}