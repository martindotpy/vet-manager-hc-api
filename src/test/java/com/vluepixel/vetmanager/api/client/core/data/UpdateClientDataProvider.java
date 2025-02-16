package com.vluepixel.vetmanager.api.client.core.data;

import static com.vluepixel.vetmanager.api.auth.core.data.LoginUserDataProvider.VALID_LOGIN_ADMIN_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_BLANK_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_EMPTY_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_NULL_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_FIRSTNAME_TOO_LONG_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_BLANK_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_EMPTY_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_NULL_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.INVALID_LASTNAME_TOO_LONG_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_REQUEST;
import static com.vluepixel.vetmanager.api.auth.core.data.RegisterUserDataProvider.generateEmail;

import java.util.ArrayList;

import com.vluepixel.vetmanager.api.client.core.domain.enums.IdentificationType;
import com.vluepixel.vetmanager.api.client.core.domain.request.UpdateClientRequest;

/**
 * Update client data provider.
 */
public class UpdateClientDataProvider {
    public static final UpdateClientRequest INVALID_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(10L)
            .firstName("New first name")
            .lastName("New first name")
            .identification("90990009")
            .identificationType(IdentificationType.DNI)
            .address("New direction")
            .emails(new ArrayList<String>() {
                {
                    add("newemail@newemail.com");
                    add("othernewemail@othernewemail.com");
                }
            })
            .phones(new ArrayList<String>() {
                {
                    add("911111111");
                    add("922222222");
                }
            })
            .build();

    public static final UpdateClientRequest VALID_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(1L)
            .firstName("New first name")
            .lastName("New last name")
            .identification("90990009")
            .identificationType(IdentificationType.DNI)
            .address("New direction")
            .emails(new ArrayList<String>() {
                {
                    add("newemail@newemail.com");
                    add("othernewemail@othernewemail.com");
                }
            })
            .phones(new ArrayList<String>() {
                {
                    add("911111111");
                    add("922222222");
                }
            })
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------

    // FirstName
    public static final UpdateClientRequest INVALID_FIRSTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(INVALID_FIRSTNAME_TOO_LONG_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_FIRSTNAME_MAX_LENGTH_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(INVALID_FIRSTNAME_BLANK_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(INVALID_FIRSTNAME_EMPTY_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(INVALID_FIRSTNAME_NULL_REGISTER_USER_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // LastName
    public static final UpdateClientRequest INVALID_LASTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_TOO_LONG_REGISTER_USER_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_LASTNAME_MAX_LENGTH_REGISTER_USER_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_BLANK_REGISTER_USER_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_EMPTY_REGISTER_USER_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_NULL_REGISTER_USER_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Identification
    public static final int MAX_IDENTIFICATION_LENGTH = 12;
    public static final UpdateClientRequest INVALID_IDENTIFICATION_TOO_LONG_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification("1".repeat(MAX_IDENTIFICATION_LENGTH + 1))
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification("1".repeat(MAX_IDENTIFICATION_LENGTH))
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(" ")
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification("")
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(null)
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Address
    public static final int MAX_ADDRESS_LENGTH = 125;
    public static final UpdateClientRequest INVALID_ADDRESS_TOO_LONG_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address("a".repeat(MAX_ADDRESS_LENGTH + 1))
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address("a".repeat(MAX_ADDRESS_LENGTH))
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(" ")
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address("")
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(null)
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Emails
    public static final int MAX_EMAIL_LENGTH = 50;
    public static final UpdateClientRequest INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add(VALID_LOGIN_ADMIN_REQUEST.getEmail());
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add("secondclient@secondclient.com");
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add("invalid");
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_EMAILS_TOO_LONG_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add(generateEmail(MAX_EMAIL_LENGTH + 1));
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add(generateEmail(MAX_EMAIL_LENGTH));
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add(" ");
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add("");
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add(null);
                    add(generateEmail(MAX_EMAIL_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Phones
    public static final int MAX_PHONE_LENGTH = 9;
    public static final UpdateClientRequest INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(new ArrayList<>() {
                {
                    add("922222222");
                    add("4".repeat(MAX_PHONE_LENGTH));
                }
            })
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final UpdateClientRequest INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(new ArrayList<>() {
                {
                    add("invalid");
                    add("9".repeat(MAX_PHONE_LENGTH));
                }
            })
            .build();

    public static final UpdateClientRequest INVALID_PHONES_TOO_LONG_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(new ArrayList<>() {
                {
                    add("9".repeat(MAX_PHONE_LENGTH + 1));
                    add("9".repeat(MAX_PHONE_LENGTH));
                }
            })
            .build();

    public static final UpdateClientRequest VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(new ArrayList<>() {
                {
                    add("9".repeat(MAX_PHONE_LENGTH));
                    add("9".repeat(MAX_PHONE_LENGTH));
                }
            })
            .build();

    public static final UpdateClientRequest INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(new ArrayList<>() {
                {
                    add(" ");
                    add("9".repeat(MAX_PHONE_LENGTH));
                }
            })
            .build();

    public static final UpdateClientRequest INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(new ArrayList<>() {
                {
                    add("");
                    add("9".repeat(MAX_PHONE_LENGTH));
                }
            })
            .build();

    public static final UpdateClientRequest INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST = UpdateClientRequest
            .builder()
            .id(VALID_UPDATE_CLIENT_REQUEST.getId())
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(new ArrayList<>() {
                {
                    add(null);
                    add("9".repeat(MAX_PHONE_LENGTH));
                }
            })
            .build();
}
