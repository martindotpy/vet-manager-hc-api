package com.vluepixel.vetmanager.api.client.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_ADDRESS_BLANK_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_ADDRESS_EMPTY_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_ADDRESS_NULL_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_ADDRESS_TOO_LONG_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_EMAILS_ALREADY_IN_USE_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_EMAILS_BLANK_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_EMAILS_EMPTY_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_EMAILS_INVALID_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_EMAILS_NULL_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_EMAILS_TOO_LONG_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_FIRSTNAME_BLANK_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_FIRSTNAME_EMPTY_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_FIRSTNAME_NULL_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_FIRSTNAME_TOO_LONG_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_IDENTIFICATION_BLANK_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_IDENTIFICATION_EMPTY_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_IDENTIFICATION_NULL_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_IDENTIFICATION_TOO_LONG_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_LASTNAME_BLANK_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_LASTNAME_EMPTY_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_LASTNAME_NULL_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_LASTNAME_TOO_LONG_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_PHONES_ALREADY_IN_USE_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_PHONES_BLANK_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_PHONES_EMPTY_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_PHONES_INVALID_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_PHONES_NULL_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.INVALID_PHONES_TOO_LONG_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.VALID_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.CreateClientDataProvider.VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the create client functionality.
 */
public class CreateClientIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER

    // Role: ADMIN
    @Test
    @Order(1)
    @DirtiesContext
    void admin_CreateClientWithValidArguments_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name").value(VALID_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name").value(VALID_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification").value(VALID_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_CREATE_CLIENT_REQUEST.getIdentificationType().toString()),
                        jsonPath("$.content.address").value(VALID_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()").value(VALID_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()").value(VALID_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    // - Validations

    // FirstName

    @Test
    void admin_CreateClientWithInvalidArguments_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(2)
    @DirtiesContext
    void admin_CreateClientWithValidArguments_FirstName_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name")
                                .value(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_CreateClientWithInvalidArguments_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // LastName
    @Test
    void admin_CreateClientWithInvalidArguments_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOO_LONG_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(3)
    @DirtiesContext
    void admin_CreateClientWithValidArguments_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name")
                                .value(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_CreateClientWithInvalidArguments_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Identification
    @Test
    void admin_CreateClientWithInvalidArguments_Identification_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_TOO_LONG_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(4)
    @DirtiesContext
    void admin_CreateClientWithValidArguments_Identification_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Identification_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_BLANK_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Identification_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_EMPTY_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Identification_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_NULL_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Address
    @Test
    void admin_CreateClientWithInvalidArguments_Address_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_TOO_LONG_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(5)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_Address_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name")
                                .value(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Address_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_BLANK_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Address_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_EMPTY_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Address_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_NULL_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Emails
    @Test
    void admin_CreateClientWithInvalidArguments_Emails_AlreadyInUseUser_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Emails_AlreadyInUse_Conclict() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADY_IN_USE_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Emails_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_INVALID_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Emails_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_TOO_LONG_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(6)
    @DirtiesContext
    void admin_CreateClientWithValidArguments_Emails_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name")
                                .value(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Emails_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_BLANK_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Emails_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_EMPTY_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Emails_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_NULL_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    // Phones
    @Test
    void admin_CreateClientWithInvalidArguments_Phones_AlreadyInUse_Conflict() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_ALREADY_IN_USE_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isConflict());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Phones_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_INVALID_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Phones_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_TOO_LONG_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(7)
    @DirtiesContext
    void admin_CreateClientWithValidArguments_Phones_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.first_name")
                                .value(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Phones_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_BLANK_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Phones_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_EMPTY_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void admin_CreateClientWithInvalidArguments_Phones_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_NULL_CREATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity());
    }
}
