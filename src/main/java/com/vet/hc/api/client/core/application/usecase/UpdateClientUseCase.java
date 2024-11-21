package com.vet.hc.api.client.core.application.usecase;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.vet.hc.api.client.core.application.mapper.ClientMapper;
import com.vet.hc.api.client.core.application.port.in.UpdateClientPort;
import com.vet.hc.api.client.core.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.client.core.domain.model.FullDataClient;
import com.vet.hc.api.client.core.domain.payload.UpdateFullDataClientPayload;
import com.vet.hc.api.client.core.domain.repository.ClientRepository;
import com.vet.hc.api.client.email.domain.model.ClientEmail;
import com.vet.hc.api.client.email.domain.repository.ClientEmailRepository;
import com.vet.hc.api.client.phone.domain.model.ClientPhone;
import com.vet.hc.api.client.phone.domain.repository.ClientPhoneRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for updating a client.
 */
@NoArgsConstructor
public class UpdateClientUseCase implements UpdateClientPort {
    private ClientRepository clientRepository;
    private ClientEmailRepository clientEmailRepository;
    private ClientPhoneRepository clientPhoneRepository;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Inject
    public UpdateClientUseCase(ClientRepository clientRepository, ClientEmailRepository clientEmailRepository,
            ClientPhoneRepository clientPhoneRepository) {
        this.clientRepository = clientRepository;
        this.clientEmailRepository = clientEmailRepository;
        this.clientPhoneRepository = clientPhoneRepository;
    }

    @Override
    public Result<FullDataClientDto, ClientFailure> update(UpdateFullDataClientPayload payload) {
        Optional<Client> clientPreUpdate = clientRepository.findById(payload.getId());

        if (clientPreUpdate.isEmpty())
            return Result.failure(ClientFailure.NOT_FOUND);

        Client clientFound = clientPreUpdate.get();

        Set<ClientEmail> emailsPreUpdate = clientFound.getEmails();
        Set<ClientPhone> phonesPreUpdate = clientFound.getPhones();
        Set<ClientEmail> emails = giveIdToEmails(payload.getEmails(), emailsPreUpdate, clientFound);
        Set<ClientPhone> phones = giveIdToPhones(payload.getPhones(), phonesPreUpdate, clientFound);

        // Delete the emails and phones that are not in the update payload
        deleteEmails(emailsPreUpdate, emails);
        deletePhones(phonesPreUpdate, phones);

        var emailResults = emails.stream().map(email -> {
            ClientEmail clientEmail = ClientEmail.builder()
                    .id(email.getId())
                    .email(email.getEmail())
                    .client(clientFound)
                    .build();

            return clientEmailRepository.save(clientEmail);
        }).collect(Collectors.toSet());
        var emailFailures = emailResults.stream().filter(result -> result.isFailure()).collect(Collectors.toSet());
        if (!emailFailures.isEmpty()) {
            return Result.failure(ClientFailure.EMAIL_SAVE_ERROR);
        }

        var phonesResults = phones.stream().map(phone -> {
            ClientPhone clientPhone = ClientPhone.builder()
                    .id(phone.getId())
                    .phone(phone.getPhone())
                    .client(clientFound)
                    .build();

            return clientPhoneRepository.save(clientPhone);
        }).collect(Collectors.toSet());

        var phoneFailures = phonesResults.stream().filter(result -> result.isFailure()).collect(Collectors.toSet());
        if (!phoneFailures.isEmpty()) {
            return Result.failure(ClientFailure.PHONE_SAVE_ERROR);
        }

        emails = emailResults.stream().map(result -> result.getSuccess()).collect(Collectors.toSet());
        phones = phonesResults.stream().map(result -> result.getSuccess()).collect(Collectors.toSet());

        Client clientUpdated = clientRepository.save(
                Client.builder()
                        .id(clientFound.getId())
                        .firstName(payload.getFirstName())
                        .lastName(payload.getLastName())
                        .identification(payload.getIdentification())
                        .identificationType(payload.getIdentificationType())
                        .address(payload.getAddress())
                        .emails(emails)
                        .phones(phones)
                        .build());

        FullDataClient fullDataClient = FullDataClient.builder()
                .client(clientUpdated)
                .emails(emails)
                .phones(phones)
                .build();

        return Result.success(clientMapper.toDto(fullDataClient));
    }

    private Set<ClientPhone> giveIdToPhones(Set<String> phones, Set<ClientPhone> phonesPreUpdate, Client client) {
        return phones.stream()
                .map(phone -> {
                    Optional<ClientPhone> phonePreUpdate = phonesPreUpdate.stream()
                            .filter(phonePreUpdateItem -> phonePreUpdateItem.getPhone().equals(phone))
                            .findFirst();

                    if (phonePreUpdate.isPresent()) {
                        return ClientPhone.builder()
                                .id(phonePreUpdate.get().getId())
                                .phone(phone)
                                .client(client)
                                .build();
                    }

                    return ClientPhone.builder()
                            .phone(phone)
                            .client(client)
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private Set<ClientEmail> giveIdToEmails(Set<String> emails, Set<ClientEmail> emailsPreUpdate, Client client) {
        return emails.stream()
                .map(email -> {
                    Optional<ClientEmail> emailPreUpdate = emailsPreUpdate.stream()
                            .filter(emailPreUpdateItem -> emailPreUpdateItem.getEmail().equals(email))
                            .findFirst();

                    if (emailPreUpdate.isPresent()) {
                        return ClientEmail.builder()
                                .id(emailPreUpdate.get().getId())
                                .email(email)
                                .client(client)
                                .build();
                    }

                    return ClientEmail.builder()
                            .email(email)
                            .client(client)
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private void deleteEmails(Set<ClientEmail> emailsPreUpdate, Set<ClientEmail> emails) {
        emailsPreUpdate.stream()
                .filter(email -> emails.stream()
                        .noneMatch(emailPreUpdate -> emailPreUpdate.getEmail().equals(email.getEmail())))
                .forEach(email -> clientEmailRepository.deleteById(email.getId()));
    }

    private void deletePhones(Set<ClientPhone> phonesPreUpdate, Set<ClientPhone> phones) {
        phonesPreUpdate.stream()
                .filter(phone -> phones.stream()
                        .noneMatch(phonePreUpdate -> phonePreUpdate.getPhone().equals(phone.getPhone())))
                .forEach(phone -> clientPhoneRepository.deleteById(phone.getId()));
    }
}
