package com.vet.hc.api.client.application.service;

import java.util.Optional;

import com.vet.hc.api.client.adapter.out.mapper.ClientMapper;
import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.application.port.in.LoadClientPort;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.repository.FullDataClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to load a client.
 */
@NoArgsConstructor
public class LoadClientService implements LoadClientPort {
    private FullDataClientRepository fullDataClientRepository;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Inject
    public LoadClientService(FullDataClientRepository fullDataClientRepository) {
        this.fullDataClientRepository = fullDataClientRepository;
    }

    @Override
    public Result<FullDataClientDto, ClientFailure> findById(Long id) {
        Optional<FullDataClientDto> fullDataClient = fullDataClientRepository.findByClientId(id)
                .map(clientMapper::toDto);

        if (fullDataClient.isEmpty()) {
            return Result.failure(new ClientFailure());
        }

        return Result.success(fullDataClient.get());
    }
}
