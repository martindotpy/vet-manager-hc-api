package com.vluepixel.vetmanager.api.client.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_ADDRESS_TOO_LONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_EMAILS_TOO_LONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_FIRSTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_IDENTIFICATION_TOO_LONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_LASTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_PHONES_TOO_LONG_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.INVALID_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_UPDATE_CLIENT_REQUEST;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
    private static final String MESSAGE_OK = "Cliente actualizado";
    private static final String MESSAGE_NOT_FOUND = "Cliente no encontrado(a)";
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

    // - Validations

    // FirstName
    @Test
    void noUser_CreateClientWithInvalidArguments_FirstName_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithValidArguments_FirstName_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_FirstName_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_FirstName_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_FirstName_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // LastName
    @Test
    void noUser_CreateClientWithInvalidArguments_LastName_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithValidArguments_LastName_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_LastName_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_LastName_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_LastName_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Identification
    @Test
    void noUser_CreateClientWithInvalidArguments_Identification_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_TOO_LONG_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithValidArguments_Identification_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Identification_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Identification_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Identification_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Address
    @Test
    void noUser_CreateClientWithInvalidArguments_Address_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_TOO_LONG_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateClientWithValidArguments_Address_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Address_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Address_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Address_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Emails
    @Test
    void noUser_CreateClientWithInvalidArguments_Emails_AlreadyInUseUser_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Emails_AlreadyInUse_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Emails_Invalid_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Emails_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_TOO_LONG_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithValidArguments_Emails_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Emails_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Emails_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Emails_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Phones
    @Test
    void noUser_CreateClientWithInvalidArguments_Phones_AlreadyInUse_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Phones_Invalid_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Phones_TooLong_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_TOO_LONG_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithValidArguments_Phones_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Phones_Blank_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Phones_Empty_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateClientWithInvalidArguments_Phones_Null_Forbidden() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    @Order(1)
    @DirtiesContext
    void user_UpdateClientWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
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
    void user_UpdateClientWithInvalidArguments_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    // - Validations

    // FirstName
    @Test
    void user_UpdateClientWithInvalidArguments_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre no puede tener más de 50 caracteres"));
    }

    @Test
    @Order(2)
    @DirtiesContext
    void user_UpdateClientWithValidArguments_FirstName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
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
    void user_UpdateClientWithInvalidArguments_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    // LastName
    @Test
    void user_UpdateClientWithInvalidArguments_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido no puede tener más de 50 caracteres"));
    }

    @Test
    @Order(3)
    @DirtiesContext
    void user_UpdateClientWithValidArguments_LastName_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
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
    void user_UpdateClientWithInvalidArguments_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    // Identification
    @Test
    void user_UpdateClientWithInvalidArguments_Identification_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación no puede tener más de 12 caracteres"));
    }

    @Test
    @Order(4)
    @DirtiesContext
    void user_UpdateClientWithValidArguments_Identification_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
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
    void user_UpdateClientWithInvalidArguments_Identification_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación es requerida"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Identification_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación es requerida"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Identification_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación es requerida"));
    }

    // Address
    @Test
    void user_UpdateClientWithInvalidArguments_Address_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección no puede tener más de 125 caracteres"));
    }

    @Test
    @Order(5)
    @DirtiesContext
    void user_UpdateClientWithValidArguments_Address_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
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
    void user_UpdateClientWithInvalidArguments_Address_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección es requerida"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Address_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección es requerida"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Address_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección es requerida"));
    }

    // Emails
    @Test
    @Order(6)
    @DirtiesContext
    void user_UpdateClientWithInvalidArguments_Emails_AlreadyInUseUser_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    @Order(7)
    @DirtiesContext
    void user_UpdateClientWithInvalidArguments_Emails_AlreadyInUse_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(1),
                        jsonPath("$.content.first_name")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Emails_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo no es válido"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Emails_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo no puede tener más de 50 caracteres"));
    }

    @Test
    @Order(8)
    @DirtiesContext
    void user_UpdateClientWithValidArguments_Emails_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
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
    void user_UpdateClientWithInvalidArguments_Emails_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El correo no es válido", "El correo es requerido")));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Emails_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo es requerido"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Emails_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo es requerido"));
    }

    // Phones
    @Test
    @Order(9)
    @DirtiesContext
    void user_UpdateClientWithInvalidArguments_Phones_AlreadyInUse_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(1),
                        jsonPath("$.content.first_name")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Phones_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El teléfono no es válido"));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Phones_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El teléfono no es válido"));
    }

    @Test
    @Order(10)
    @DirtiesContext
    void user_UpdateClientWithValidArguments_Phones_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
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
    void user_UpdateClientWithInvalidArguments_Phones_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El teléfono es requerido", "El teléfono no es válido")));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Phones_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El teléfono es requerido", "El teléfono no es válido")));
    }

    @Test
    void user_UpdateClientWithInvalidArguments_Phones_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El teléfono es requerido"));
    }

    // Role: ADMIN
    @Test
    @Order(11)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
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
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    // - Validations

    // FirstName
    @Test
    void admin_UpdateClientWithInvalidArguments_FirstName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre no puede tener más de 50 caracteres"));
    }

    @Test
    @Order(12)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_FirstName_MaxLength_Ok() throws Exception {
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
    void admin_UpdateClientWithInvalidArguments_FirstName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_FirstName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_FirstName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("first_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    // LastName
    @Test
    void admin_UpdateClientWithInvalidArguments_LastName_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido no puede tener más de 50 caracteres"));
    }

    @Test
    @Order(13)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_LastName_MaxLength_Ok() throws Exception {
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
    void admin_UpdateClientWithInvalidArguments_LastName_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_LastName_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_LastName_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("last_name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El apellido es requerido"));
    }

    // Identification
    @Test
    void admin_UpdateClientWithInvalidArguments_Identification_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación no puede tener más de 12 caracteres"));
    }

    @Test
    @Order(14)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_Identification_MaxLength_Ok() throws Exception {
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
    void admin_UpdateClientWithInvalidArguments_Identification_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación es requerida"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Identification_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación es requerida"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Identification_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La identificación es requerida"));
    }

    // Address
    @Test
    void admin_UpdateClientWithInvalidArguments_Address_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección no puede tener más de 125 caracteres"));
    }

    @Test
    @Order(15)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_Address_MaxLength_Ok() throws Exception {
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
    void admin_UpdateClientWithInvalidArguments_Address_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección es requerida"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Address_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección es requerida"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Address_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("address"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("La dirección es requerida"));
    }

    // Emails
    @Test
    @Order(16)
    @DirtiesContext
    void admin_UpdateClientWithInvalidArguments_Emails_AlreadyInUseUser_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getId()),
                        jsonPath("$.content.first_name")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    @Order(17)
    @DirtiesContext
    void admin_UpdateClientWithInvalidArguments_Emails_AlreadyInUse_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").isString(),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(1),
                        jsonPath("$.content.first_name")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Emails_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo no es válido"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Emails_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo no puede tener más de 50 caracteres"));
    }

    @Test
    @Order(18)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_Emails_MaxLength_Ok() throws Exception {
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
    void admin_UpdateClientWithInvalidArguments_Emails_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El correo no es válido", "El correo es requerido")));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Emails_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo es requerido"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Emails_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El correo es requerido"));
    }

    // Phones
    @Test
    @Order(19)
    @DirtiesContext
    void admin_UpdateClientWithInvalidArguments_Phones_AlreadyInUse_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isMap(),
                        jsonPath("$.content.id").value(1),
                        jsonPath("$.content.first_name")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getFirstName()),
                        jsonPath("$.content.last_name")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getLastName()),
                        jsonPath("$.content.identification")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentification()),
                        jsonPath("$.content.identification_type")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getIdentificationType()
                                        .toString()),
                        jsonPath("$.content.address")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getAddress()),
                        jsonPath("$.content.emails.length()")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getEmails().size()),
                        jsonPath("$.content.phones.length()")
                                .value(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getPhones().size()));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Phones_Invalid_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El teléfono no es válido"));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Phones_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_TOO_LONG_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El teléfono no es válido"));
    }

    @Test
    @Order(20)
    @DirtiesContext
    void admin_UpdateClientWithValidArguments_Phones_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
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
    void admin_UpdateClientWithInvalidArguments_Phones_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El teléfono es requerido", "El teléfono no es válido")));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Phones_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(2),
                        jsonPath("$.details[0].messages")
                                .value(containsInAnyOrder("El teléfono es requerido", "El teléfono no es válido")));
    }

    @Test
    void admin_UpdateClientWithInvalidArguments_Phones_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").isString(),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages")
                                .value("El teléfono es requerido"));
    }
}
