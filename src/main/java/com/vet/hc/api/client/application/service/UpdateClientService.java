package com.vet.hc.api.client.application.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.vet.hc.api.client.adapter.out.mapper.ClientMapper;
import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.application.port.in.UpdateClientPort;
import com.vet.hc.api.client.domain.command.UpdateFullDataClientCommand;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.model.ClientEmail;
import com.vet.hc.api.client.domain.model.ClientPhone;
import com.vet.hc.api.client.domain.model.FullDataClient;
import com.vet.hc.api.client.domain.repository.ClientEmailRepository;
import com.vet.hc.api.client.domain.repository.ClientPhoneRepository;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for updating a client.
 */
@NoArgsConstructor
public class UpdateClientService implements UpdateClientPort {
    private ClientRepository clientRepository;
    private ClientEmailRepository clientEmailRepository;
    private ClientPhoneRepository clientPhoneRepository;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Inject
    public UpdateClientService(ClientRepository clientRepository, ClientEmailRepository clientEmailRepository,
            ClientPhoneRepository clientPhoneRepository) {
        this.clientRepository = clientRepository;
        this.clientEmailRepository = clientEmailRepository;
        this.clientPhoneRepository = clientPhoneRepository;
    }

    @Override
    public Result<FullDataClientDto, ClientFailure> update(Long id, UpdateFullDataClientCommand command) {
        if (!id.equals(command.getClient().getId()))
            return Result.failure(ClientFailure.ID_MISSMATCH);

        Optional<Client> clientPreUpdate = clientRepository.findById(id);

        if (clientPreUpdate.isEmpty())
            return Result.failure(ClientFailure.NOT_FOUND);

        Client clientFound = clientPreUpdate.get();

        Set<ClientEmail> emailsPreUpdate = clientFound.getEmails();
        Set<ClientPhone> phonesPreUpdate = clientFound.getPhones();

        Set<ClientEmail> emailsToUpdate = command.getEmails().stream().filter(email -> email.getId() != null)
                .collect(Collectors.toSet());
        Set<ClientPhone> phonesToUpdate = command.getPhones().stream().filter(phone -> phone.getId() != null)
                .collect(Collectors.toSet());

        // Delete the emails and phones that are not in the update command
        emailsPreUpdate.forEach(email -> {
            if (!emailsToUpdate.stream().filter(e -> e.getId().equals(email.getId())).findFirst().isPresent())
                clientEmailRepository.deleteById(email.getId());
        });
        phonesPreUpdate.forEach(phone -> {
            if (!phonesToUpdate.stream().filter(p -> p.getId().equals(phone.getId())).findFirst().isPresent())
                clientPhoneRepository.deleteById(phone.getId());
        });

        Set<ClientEmail> emails = command.getEmails();
        Set<ClientPhone> phones = command.getPhones();

        // Save (persist or merge) the emails and phones
        emails = emails.stream().map(email -> {
            ClientEmail clientEmail = ClientEmail.builder()
                    .id(email.getId())
                    .email(email.getEmail())
                    .client(clientFound)
                    .build();

            var result = clientEmailRepository.save(clientEmail);

            if (result.isFailure())
                return null;

            return result.getSuccess();
        }).collect(Collectors.toSet());

        if (emails.stream().anyMatch(email -> email == null))
            return Result.failure(ClientFailure.EMAIL_SAVE_ERROR);

        phones = phones.stream().map(phone -> {
            ClientPhone clientPhone = ClientPhone.builder()
                    .id(phone.getId())
                    .phone(phone.getPhone())
                    .client(clientFound)
                    .build();

            var result = clientPhoneRepository.save(clientPhone);

            if (result.isFailure())
                return null;

            return result.getSuccess();
        }).collect(Collectors.toSet());

        if (phones.stream().anyMatch(phone -> phone == null))
            return Result.failure(ClientFailure.PHONE_SAVE_ERROR);

        Client clientUpdated = clientRepository.save(command.getClient());

        FullDataClient fullDataClient = FullDataClient.builder()
                .client(clientUpdated)
                .emails(emails)
                .phones(phones)
                .build();

        return Result.success(clientMapper.toDto(fullDataClient));
    }
}
