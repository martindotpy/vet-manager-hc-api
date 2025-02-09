package com.vet.hc.api.user.core.integration;

import static com.vet.hc.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vet.hc.api.auth.core.data.AuthDataProvider.USER_DTO;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_BLANK;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_EMPTY;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_MAX_LENGTH;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_NULL;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_BLANK;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_EMPTY;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_FIRST_NAME_BLANK;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_FIRST_NAME_EMPTY;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_FIRST_NAME_MAX_LENGTH;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_FIRST_NAME_NULL;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_LAST_NAME_BLANK;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_LAST_NAME_EMPTY;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_LAST_NAME_MAX_LENGTH;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.INVALID_UPDATE_USER_REQUEST_LAST_NAME_NULL;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_ADMIN_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_MAX_LENGTH;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_ADMIN_REQUEST_LAST_NAME_MAX_LENGTH;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_USER_REQUEST;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_USER_REQUEST_FIRST_NAME_MAX_LENGTH;
import static com.vet.hc.api.user.core.data.UpdateUserDataProvider.VALID_UPDATE_USER_REQUEST_LAST_NAME_MAX_LENGTH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vet.hc.api.base.BaseIntegrationTest;

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

    // /user/{id}
    @Test
    void noUser_UpdateUser_Forbidden() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST)))
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
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    // /user
    @Test
    @DirtiesContext
    void admin_UpdateCurrentUser_Success() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithDifferentRequestId_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    // /user/{id}
    @Test
    void admin_UpdateOtherUser_Success() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
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
        mockMvc.perform(put("/user/" + ADMIN_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Validations:
    // The validations in this case only apply to the admin user
    // -----------------------------------------------------------------------------------------------------------------

    // - First name
    // /user
    @Test
    void admin_UpdateCurrentUserWithInvalidFirstNameNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_NULL))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidFirstNameEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_EMPTY))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidFirstNameBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_BLANK))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateCurrentUserWithValidFirstNameMaxLength_Success() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidFirstNameMaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is too long"));
    }

    // /user/{id}
    @Test
    void admin_UpdateOtherUserWithInvalidFirstNameNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_FIRST_NAME_NULL))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidFirstNameEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_FIRST_NAME_EMPTY))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidFirstNameBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_FIRST_NAME_BLANK))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is required"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateOtherUserWithValidFirstNameMaxLength_Success() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST_FIRST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.id").value(USER_DTO.getId()))
                .andExpect(jsonPath("$.content.first_name")
                        .value(VALID_UPDATE_USER_REQUEST_FIRST_NAME_MAX_LENGTH.getFirstName()))
                .andExpect(jsonPath("$.content.last_name").value(USER_DTO.getLastName()))
                .andExpect(jsonPath("$.content.email").value(USER_DTO.getEmail()))
                .andExpect(jsonPath("$.content.roles").isArray())
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidFirstNameMaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_FIRST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is too long"));
    }

    // - Last name
    // /user
    @Test
    void admin_UpdateCurrentUserWithInvalidLastNameNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_BLANK))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidLastNameEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_EMPTY))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidLastNameBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_LAST_NAME_BLANK))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateCurrentUserWithValidLastNameMaxLength_Success() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_ADMIN_REQUEST_LAST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserWithInvalidLastNameMaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_ADMIN_REQUEST_FIRST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("first_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("First name is too long"));
    }

    // /user/{id}
    @Test
    void admin_UpdateOtherUserWithInvalidLastNameNull_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_LAST_NAME_NULL))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidLastNameEmpty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_LAST_NAME_EMPTY))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    void admin_UpdateOtherUserWithInvalidLastNameBlank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_LAST_NAME_BLANK))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is required"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateOtherUserWithValidLastNameMaxLength_Success() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST_LAST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.content.id").value(USER_DTO.getId()))
                .andExpect(jsonPath("$.content.first_name").value(USER_DTO.getFirstName()))
                .andExpect(jsonPath("$.content.last_name")
                        .value(VALID_UPDATE_USER_REQUEST_LAST_NAME_MAX_LENGTH.getLastName()))
                .andExpect(jsonPath("$.content.email").value(USER_DTO.getEmail()))
                .andExpect(jsonPath("$.content.roles").isArray())
                .andExpect(jsonPath("$.content.roles.length()").value(1))
                .andExpect(jsonPath("$.content.roles[0]").value("USER"))
                .andExpect(jsonPath("$.content.profile_image_url").isEmpty());
    }

    @Test
    void admin_UpdateOtherUserWithInvalidLastNameMaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/user/" + USER_DTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_USER_REQUEST_LAST_NAME_MAX_LENGTH))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.details.length()").value(1))
                .andExpect(jsonPath("$.details[0].field").value("last_name"))
                .andExpect(jsonPath("$.details[0].messages[0]").value("Last name is too long"));
    }
}