package com.vluepixel.vetmanager.api.species.core.integration;

import static com.vluepixel.vetmanager.api.species.core.data.CreateSpeciesDataProvider.INVALID_NAME_BLANK_CREATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.CreateSpeciesDataProvider.INVALID_NAME_EMPTY_CREATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.CreateSpeciesDataProvider.INVALID_NAME_NULL_CREATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.CreateSpeciesDataProvider.INVALID_NAME_TOO_LONG_CREATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.CreateSpeciesDataProvider.VALID_CREATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.species.core.data.CreateSpeciesDataProvider.VALID_NAME_MAX_LENGTH_CREATE_SPECIES_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration test for the create species functionality.
 */
public class CreateSpeciesIntegrationTest extends BaseIntegrationTest {
    private final static String MESSAGE_OK = "Especie creada exitosamente";
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    @DirtiesContext
    void noUser_CreateSpeciesWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_CREATE_SPECIES_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // - Validations

    // ID
    @Test
    void noUser_CreateSpeciesWithInvalidArguments_Name_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_CREATE_SPECIES_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateSpeciesWithValidArguments_Name_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_CREATE_SPECIES_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateSpeciesWithInvalidArguments_Name_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_CREATE_SPECIES_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateSpeciesWithInvalidArguments_Name_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_CREATE_SPECIES_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateSpeciesWithInvalidArguments_Name_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_CREATE_SPECIES_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    @DirtiesContext
    void user_CreateSpeciesWithValidArguments_Ok() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.name").value(VALID_CREATE_SPECIES_REQUEST.getName()));
    }

    // - Validations

    // ID
    @Test
    void user_CreateSpeciesWithInvalidArguments_Name_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre no puede tener más de 50 caracteres"));
    }

    @Test
    @DirtiesContext
    void user_CreateSpeciesWithValidArguments_Name_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.name").value(VALID_NAME_MAX_LENGTH_CREATE_SPECIES_REQUEST.getName()));
    }

    @Test
    void user_CreateSpeciesWithInvalidArguments_Name_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void user_CreateSpeciesWithInvalidArguments_Name_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void user_CreateSpeciesWithInvalidArguments_Name_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    // Role: ADMIN
    @Test
    @DirtiesContext
    void admin_CreateSpeciesWithValidArguments_Ok() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.name").value(VALID_CREATE_SPECIES_REQUEST.getName()));
    }

    // - Validations

    // ID
    @Test
    void admin_CreateSpeciesWithInvalidArguments_Name_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre no puede tener más de 50 caracteres"));
    }

    @Test
    @DirtiesContext
    void admin_CreateSpeciesWithValidArguments_Name_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(3),
                        jsonPath("$.content.name").value(VALID_NAME_MAX_LENGTH_CREATE_SPECIES_REQUEST.getName()));
    }

    @Test
    void admin_CreateSpeciesWithInvalidArguments_Name_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_CreateSpeciesWithInvalidArguments_Name_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_CreateSpeciesWithInvalidArguments_Name_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_CREATE_SPECIES_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }
}
