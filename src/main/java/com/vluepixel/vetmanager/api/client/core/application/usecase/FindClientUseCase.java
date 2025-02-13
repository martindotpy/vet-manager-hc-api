package com.vluepixel.vetmanager.api.client.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.client.core.application.mapper.ClientMapper;
import com.vluepixel.vetmanager.api.client.core.application.port.in.FindClientPort;
import com.vluepixel.vetmanager.api.client.core.domain.model.Client;
import com.vluepixel.vetmanager.api.client.core.domain.repository.ClientRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find client.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindClientUseCase implements FindClientPort {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Paginated<ClientDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Criteria " + criteria);
        log.info("Finding all client");

        var paginated = clientRepository.findPaginatedBy(criteria);

        log.info("Retrieved {} client ",
                fgBrightGreen(paginated.getSize()));

        return paginated.map(clientMapper::toDto);
    }

    @Override
    public ClientDto findById(Long id) {
        MDC.put("operationId", "Id " + id);
        log.info("Finding client  by id");

        var client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Client.class, id));

        log.info("Retrieved client  {}", fgBrightGreen(client));

        return clientMapper.toDto(client);
    }
}
