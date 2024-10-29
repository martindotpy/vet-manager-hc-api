package com.vet.hc.api.client.application.service;

import com.vet.hc.api.client.adapter.out.mapper.ClientMapper;
import com.vet.hc.api.client.application.dto.FullDataClientDto;
import com.vet.hc.api.client.application.port.in.LoadClientPort;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.repository.ClientEmailRepository;
import com.vet.hc.api.client.domain.repository.ClientPhoneRepository;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.NoArgsConstructor;

/**
 * Service to load a client.
 */
@NoArgsConstructor
public class LoadClientService implements LoadClientPort {
    private ClientRepository clientRepository;
    private ClientEmailRepository clientEmailRepository;
    private ClientPhoneRepository clientPhoneRepository;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Override
    public Result<FullDataClientDto, ClientFailure> findById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
