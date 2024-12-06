package com.vet.hc.api.client.core.application.usecase;

import java.util.Set;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.client.core.application.mapper.ClientMapper;
import com.vet.hc.api.client.core.application.port.in.CreateClientPort;
import com.vet.hc.api.client.core.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.client.core.domain.payload.CreateClientPayload;
import com.vet.hc.api.client.core.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Service for creating a new client.
 */
@UseCase
@RequiredArgsConstructor
public final class CreateClientUseCase implements CreateClientPort {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Result<FullDataClientDto, ClientFailure> create(CreateClientPayload payload) {
        Client newClient = Client.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .identification(payload.getIdentification())
                .identificationType(payload.getIdentificationType())
                .address(payload.getAddress())
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
