package com.vluepixel.vetmanager.api.client.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_TOOLONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_ALREADYINUSE_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_TOOLONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_TOOLONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_TOOLONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_TOOLONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_ALREADYINUSE_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_TOOLONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_UPDATE_CLIENT_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the update client functionality.
 */
public class UpdateClientIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_UpdateClientWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_UpdateClientWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isForbidden());
    }

    // Role: ADMIN
    @Test
    @Order(1)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(VALID_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name").value(VALID_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification").value(VALID_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType().toString()),
                        jsonPath("$.content.address").value(VALID_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()").value(VALID_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()").value(VALID_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound());
    }

    // - Validations

    // FirstName
    @Test
    void admin_UpdateClietWithInvalidArguments_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOOLONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(2)
    @DirtiesContext
    void admin_UpdateClietWithValidArguments_FirstName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // LastName
    @Test
    void admin_UpdateClietWithInvalidArguments_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOOLONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(3)
    @DirtiesContext
    void admin_UpdateClietWithValidArguments_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Identification
    @Test
    void admin_UpdateClietWithInvalidArguments_Identification_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_TOOLONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(4)
    @DirtiesContext
    void admin_UpdateClietWithValidArguments_Identification_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Identification_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Identification_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Identification_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Address
    @Test
    void admin_UpdateClietWithInvalidArguments_Address_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_TOOLONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithValidArguments_Address_MaxLength_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Address_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Address_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Address_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Emails
    @Test
    void admin_UpdateClietWithInvalidArguments_Emails_AlreadyInUseUser_Conclict() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Emails_AlreadyInUse_Conclict() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADYINUSE_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Emails_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Emails_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_TOOLONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(5)
    @DirtiesContext
    void admin_UpdateClietWithValidArguments_Emails_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Emails_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Emails_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Emails_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Phones
    @Test
    void admin_UpdateClietWithInvalidArguments_Phones_AlreadyInUse_Conflict() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_ALREADYINUSE_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Phones_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Phones_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_TOOLONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(6)
    @DirtiesContext
    void admin_UpdateClietWithValidArguments_Phones_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Phones_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Phones_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_UpdateClietWithInvalidArguments_Phones_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }
}
