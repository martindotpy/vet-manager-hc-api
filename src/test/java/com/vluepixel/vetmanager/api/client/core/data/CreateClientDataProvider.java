package com.vluepixel.vetmanager.api.client.core.data;

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
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST;
import static com.vluepixel.vetmanager.api.client.core.data.UpdateClientDataProvider.VALID_UPDATE_CLIENT_REQUEST;

import com.vluepixel.vetmanager.api.client.core.domain.request.CreateClientRequest;

/**
 * Create client data provider.
 */
public class CreateClientDataProvider {
    public static final CreateClientRequest VALID_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // -----------------------------------------------------------------------------------------------------------------
    // Validations
    // -----------------------------------------------------------------------------------------------------

    // FirstName
    public static final CreateClientRequest INVALID_FIRSTNAME_TOO_LONG_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(INVALID_FIRSTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest VALID_FIRSTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_FIRSTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_FIRSTNAME_BLANK_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(INVALID_FIRSTNAME_BLANK_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_FIRSTNAME_EMPTY_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(INVALID_FIRSTNAME_EMPTY_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_FIRSTNAME_NULL_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(INVALID_FIRSTNAME_NULL_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // LastName
    public static final CreateClientRequest INVALID_LASTNAME_TOO_LONG_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_TOO_LONG_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest VALID_LASTNAME_MAXLENGTH_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_LASTNAME_MAXLENGTH_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_LASTNAME_BLANK_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_BLANK_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_LASTNAME_EMPTY_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_EMPTY_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_LASTNAME_NULL_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(INVALID_LASTNAME_NULL_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Identification
    public static final CreateClientRequest INVALID_IDENTIFICATION_TOO_LONG_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(INVALID_IDENTIFICATION_TOO_LONG_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest VALID_IDENTIFICATION_MAXLENGTH_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_IDENTIFICATION_MAXLENGTH_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_IDENTIFICATION_BLANK_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(INVALID_IDENTIFICATION_BLANK_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_IDENTIFICATION_EMPTY_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(INVALID_IDENTIFICATION_EMPTY_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_IDENTIFICATION_NULL_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(INVALID_IDENTIFICATION_NULL_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Address
    public static final CreateClientRequest INVALID_ADDRESS_TOO_LONG_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(INVALID_ADDRESS_TOO_LONG_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest VALID_ADDRESS_MAXLENGTH_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_ADDRESS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_ADDRESS_BLANK_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(INVALID_ADDRESS_BLANK_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_ADDRESS_EMPTY_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(INVALID_ADDRESS_EMPTY_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_ADDRESS_NULL_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(INVALID_ADDRESS_NULL_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Emails
    public static final CreateClientRequest INVALID_EMAILS_ALREADYINUSEUSER_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(INVALID_EMAILS_ALREADYINUSEUSER_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_EMAILS_ALREADY_IN_USE_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(INVALID_EMAILS_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_EMAILS_INVALID_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(INVALID_EMAILS_INVALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_EMAILS_TOO_LONG_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(INVALID_EMAILS_TOO_LONG_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest VALID_EMAILS_MAXLENGTH_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_EMAILS_MAXLENGTH_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_EMAILS_BLANK_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(INVALID_EMAILS_BLANK_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_EMAILS_EMPTY_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(INVALID_EMAILS_EMPTY_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_EMAILS_NULL_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(INVALID_EMAILS_NULL_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    // Phones
    public static final CreateClientRequest INVALID_PHONES_ALREADY_IN_USE_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(INVALID_PHONES_ALREADY_IN_USE_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_PHONES_INVALID_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(INVALID_PHONES_INVALID_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_PHONES_TOO_LONG_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(INVALID_PHONES_TOO_LONG_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest VALID_PHONES_MAXLENGTH_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(VALID_PHONES_MAXLENGTH_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_PHONES_BLANK_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(INVALID_PHONES_BLANK_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_PHONES_EMPTY_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(INVALID_PHONES_EMPTY_UPDATE_CLIENT_REQUEST.getPhones())
            .build();

    public static final CreateClientRequest INVALID_PHONES_NULL_CREATE_CLIENT_REQUEST = CreateClientRequest
            .builder()
            .firstName(VALID_UPDATE_CLIENT_REQUEST.getFirstName())
            .lastName(VALID_UPDATE_CLIENT_REQUEST.getLastName())
            .identification(VALID_UPDATE_CLIENT_REQUEST.getIdentification())
            .identificationType(VALID_UPDATE_CLIENT_REQUEST.getIdentificationType())
            .address(VALID_UPDATE_CLIENT_REQUEST.getAddress())
            .emails(VALID_UPDATE_CLIENT_REQUEST.getEmails())
            .phones(INVALID_PHONES_NULL_UPDATE_CLIENT_REQUEST.getPhones())
            .build();
}
