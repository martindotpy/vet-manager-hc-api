package com.vluepixel.vetmanager.api.client.core.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.client.core.application.mapper.ClientMapper;
import com.vluepixel.vetmanager.api.client.core.application.port.in.CreateClientPort;
import com.vluepixel.vetmanager.api.client.core.domain.repository.ClientRepository;
import com.vluepixel.vetmanager.api.client.core.domain.request.CreateClientRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create client use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateClientUseCase implements CreateClientPort {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDto create(CreateClientRequest request) {
        MDC.put("operationId", "Client last name " + request.getLastName());
        log.info("Creating client");

        var newClient = clientMapper.fromRequest(request).build();
        newClient = clientRepository.save(newClient);

        log.info("Client created");

        return clientMapper.toDto(newClient);
    }
}
