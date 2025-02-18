package com.vluepixel.vetmanager.api.race.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.INVALID_NAME_BLANK_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.INVALID_NAME_EMPTY_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.INVALID_NAME_NULL_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.INVALID_NAME_TOO_LONG_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.INVALID_SPECIES_ID_NEGATIVE_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.INVALID_SPECIES_ID_NOT_FOUND_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.INVALID_SPECIES_ID_NULL_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.VALID_CREATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.CreateRaceDataProvider.VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the create race functionality.
 */
public class CreateRaceIntegrationTest extends BaseIntegrationTest {
    private static final String MESSAGE_OK = "Raza creada exitosamente";
    private static final String MESSAGE_NOT_FOUND = "Especie no encontrado(a)";
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    @DirtiesContext
    void noUser_CreateRaceWithValidArguments_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateRaceWithInvalidArguments_Name_TooLong_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    @DirtiesContext
    void noUser_CreateRaceWithValidArguments_Name_MaxLength_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateRaceWithInvalidArguments_Name_Blank_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateRaceWithInvalidArguments_Name_Empty_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateRaceWithInvalidArguments_Name_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Species ID
    @Test
    void noUser_CreateRaceWithInvalidArguments_SpeciesID_NotFound_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NOT_FOUND_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateRaceWithInvalidArguments_SpeciesID_Negative_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NEGATIVE_CREATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_CreateRaceWithInvalidArguments_SpeciesID_Null_Forbidden() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NULL_CREATE_RACE_REQUEST)))
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
    void user_CreateRaceWithValidArguments_Ok() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(5),
                        jsonPath("$.content.name").value(VALID_CREATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id").value(VALID_CREATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    @Test
    void user_CreateRaceWithInvalidArguments_Name_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_CREATE_RACE_REQUEST))
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
    @Order(2)
    @DirtiesContext
    void user_CreateRaceWithValidArguments_Name_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(5),
                        jsonPath("$.content.name").value(VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id")
                                .value(VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    @Test
    void user_CreateRaceWithInvalidArguments_Name_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_CREATE_RACE_REQUEST))
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
    void user_CreateRaceWithInvalidArguments_Name_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_CREATE_RACE_REQUEST))
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
    void user_CreateRaceWithInvalidArguments_Name_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_CREATE_RACE_REQUEST))
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

    // Species ID
    @Test
    void user_CreateRaceWithInvalidArguments_SpeciesID_NotFound_NotFound() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NOT_FOUND_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isNotFound())
                .andExpectAll(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    @Test
    void user_CreateRaceWithInvalidArguments_SpeciesID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NEGATIVE_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie debe ser mayor a 0"));
    }

    @Test
    void user_CreateRaceWithInvalidArguments_SpeciesID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NULL_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie es requerido"));
    }

    // Role: ADMIN
    @Test
    @Order(3)
    @DirtiesContext
    void admin_CreateRaceWithValidArguments_Ok() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(5),
                        jsonPath("$.content.name").value(VALID_CREATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id").value(VALID_CREATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    @Test
    void admin_CreateRaceWithInvalidArguments_Name_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_CREATE_RACE_REQUEST))
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
    @Order(4)
    @DirtiesContext
    void admin_CreateRaceWithValidArguments_Name_MaxLength_Ok() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(5),
                        jsonPath("$.content.name").value(VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id")
                                .value(VALID_NAME_MAX_LENGTH_CREATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    @Test
    void admin_CreateRaceWithInvalidArguments_Name_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_CREATE_RACE_REQUEST))
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
    void admin_CreateRaceWithInvalidArguments_Name_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_CREATE_RACE_REQUEST))
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
    void admin_CreateRaceWithInvalidArguments_Name_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_CREATE_RACE_REQUEST))
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

    // Species ID
    @Test
    void admin_CreateRaceWithInvalidArguments_SpeciesID_NotFound_NotFound() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NOT_FOUND_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpectAll(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    @Test
    void admin_CreateRaceWithInvalidArguments_SpeciesID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NEGATIVE_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie debe ser mayor a 0"));
    }

    @Test
    void admin_CreateRaceWithInvalidArguments_SpeciesID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(post("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NULL_CREATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie es requerido"));
    }
}
