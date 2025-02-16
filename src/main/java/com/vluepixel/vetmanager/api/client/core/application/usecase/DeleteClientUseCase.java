package com.vluepixel.vetmanager.api.client.core.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.client.core.application.port.in.DeleteClientPort;
import com.vluepixel.vetmanager.api.client.core.domain.repository.ClientRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete client use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteClientUseCase implements DeleteClientPort {
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void deleteById(Long id) {
        MDC.put("operationId", "Client id " + id);
        log.info("Deleting client by id");

        clientRepository.deleteById(id);

        log.info("Client deleted");
    }
}
