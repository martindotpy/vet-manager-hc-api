package com.vet.hc.api.client.application.service;

import java.util.Set;

import com.vet.hc.api.client.application.mapper.ClientMapper;
import com.vet.hc.api.client.application.port.in.CreateClientPort;
import com.vet.hc.api.client.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.payload.CreateClientPayload;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for creating a new client.
 */
@NoArgsConstructor
public class CreateClientService implements CreateClientPort {
    private ClientRepository clientRepository;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Inject
    public CreateClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Result<FullDataClientDto, ClientFailure> create(CreateClientPayload command) {
        Client newClient = Client.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .identification(command.getIdentification())
                .identificationType(command.getIdentificationType())
                .address(command.getAddress())
                .build();
        newClient = clientRepository.save(newClient);

        FullDataClientDto newClientDto = FullDataClientDto.builder()
                .client(clientMapper.toDto(newClient))
                .emails(Set.of())
                .phones(Set.of())
                .build();

        return Result.success(newClientDto);
    }
}
