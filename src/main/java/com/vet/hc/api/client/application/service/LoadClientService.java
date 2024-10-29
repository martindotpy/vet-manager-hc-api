package com.vet.hc.api.client.application.service;

import java.util.Optional;

import com.vet.hc.api.client.adapter.out.mapper.ClientMapper;
import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.application.port.in.LoadClientPort;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.model.FullDataClient;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to load a client.
 */
@NoArgsConstructor
public class LoadClientService implements LoadClientPort {
    private ClientRepository clientRepository;

    @Inject
    public LoadClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Override
    public Result<FullDataClientDto, ClientFailure> findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isEmpty()) {
            return Result.failure(ClientFailure.NOT_FOUND);
        }

        Client clientFound = client.get();
        FullDataClient fullDataClient = FullDataClient.builder()
                .client(clientFound)
                .emails(clientFound.getEmails())
                .phones(clientFound.getPhones())
                .build();

        return Result.success(clientMapper.toDto(fullDataClient));
    }
}
