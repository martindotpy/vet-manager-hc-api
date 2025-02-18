package com.vluepixel.vetmanager.api.shared.domain.util;

import static com.vluepixel.vetmanager.api.shared.domain.util.CaseConverterUtils.toSnakeCase;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Spanish utilities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpanishUtils {
    private final static Map<String, String> values = loadValues();

    /**
     * Get the Spanish name of the value.
     *
     * @param value the value.
     * @return the Spanish name
     */
    public static String getName(String value) {
        return values.getOrDefault(toSnakeCase(value), value);
    }

    /**
     * Get the Spanish name of the class.
     *
     * @param clazz the class.
     * @return the Spanish name
     */
    public static String getName(Class<?> clazz) {
        return getName(clazz.getSimpleName());
    }

    private static Map<String, String> loadValues() {
        var values = new HashMap<String, String>();

        values.put("address", "Dirección");
        values.put("age", "Edad");
        values.put("appointment", "Cita");
        values.put("appointment_details", "Detalles de la cita");
        values.put("birth_date", "Fecha de nacimiento");
        values.put("characteristics", "Características");
        values.put("client", "Cliente");
        values.put("content", "Contenido");
        values.put("created_at", "Creado en");
        values.put("created_by", "Creado por");
        values.put("deleted", "Eliminado");
        values.put("description", "Descripción");
        values.put("diagnosis", "Diagnóstico");
        values.put("deceased", "Fallecido");
        values.put("dose_in_milliliters", "Dosis en mililitros");
        values.put("duration_in_minutes", "Duración en minutos");
        values.put("details", "Detalles");
        values.put("email", "Correo electrónico");
        values.put("emails", "Correos electrónicos");
        values.put("entry_at", "Fecha de ingreso");
        values.put("first_name", "Nombre");
        values.put("genre", "Género");
        values.put("hear_rate", "Frecuencia cardíaca");
        values.put("identification", "Identificación");
        values.put("identification_type", "Tipo de identificación");
        values.put("id", "Id");
        values.put("last_name", "Apellido");
        values.put("medical_history", "Historia clínica");
        values.put("medical_histories", "Historias clínicas");
        values.put("medical_record", "Registro médico");
        values.put("medical_records", "Registros médicos");
        values.put("name", "Nombre");
        values.put("order", "Orden");
        values.put("owner", "Dueño");
        values.put("password", "Contraseña");
        values.put("patient", "Paciente");
        values.put("phone", "Teléfono");
        values.put("phones", "Teléfonos");
        values.put("physical_exam", "Examen físico");
        values.put("price", "Precio");
        values.put("profile_image_url", "Url de la imagen de perfil");
        values.put("provided_at", "Fecha de aplicación");
        values.put("race", "Raza");
        values.put("races", "Razas");
        values.put("reason", "Motivo");
        values.put("recipe", "Receta");
        values.put("respiratory_rate", "Frecuencia respiratoria");
        values.put("role", "Rol");
        values.put("roles", "Roles");
        values.put("species", "Especie");
        values.put("start_at", "Fecha de inicio");
        values.put("sterilized", "Esterilizado");
        values.put("temperature_in_celsius", "Temperatura en Celsius");
        values.put("treatment", "Tratamiento");
        values.put("treatments", "Tratamientos");
        values.put("type", "Tipo");
        values.put("updated_at", "Actualizado en");
        values.put("updated_by", "Actualizado por");
        values.put("user", "Usuario");
        values.put("vaccinator", "Vacunador");
        values.put("vaccine", "Vacuna");
        values.put("vaccines", "Vacunas");
        values.put("vet", "Veterinario");
        values.put("weight", "Peso");

        return values;
    }
}
