package com.vet.hc.api.client.application.service;

import java.util.Optional;

import com.vet.hc.api.client.application.mapper.ClientMapper;
import com.vet.hc.api.client.application.port.in.FindClientPort;
import com.vet.hc.api.client.domain.dto.ClientDto;
import com.vet.hc.api.client.domain.dto.FullDataClientDto;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.model.FullDataClient;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to load a client.
 */
@NoArgsConstructor
public class FindClientService implements FindClientPort {
    private ClientRepository clientRepository;

    @Inject
    public FindClientService(ClientRepository clientRepository) {
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

    @Override
    public Result<Paginated<ClientDto>, ClientFailure> match(Criteria criteria) {
        var response = clientRepository.match(criteria);

        if (response.isFailure()) {
            return switch (response.getFailure()) {
                case FIELD_NOT_FOUND -> Result.failure(ClientFailure.FIELD_NOT_FOUND);
                default -> Result.failure(ClientFailure.UNEXPECTED);
            };
        }

        var success = response.getSuccess();

        return Result.success(Paginated.<ClientDto>builder()
                .content(success.getContent().stream()
                        .map(clientMapper::toDto)
                        .toList())
                .page(success.getPage())
                .size(success.getSize())
                .totalElements(success.getTotalElements())
                .totalPages(success.getTotalPages())
                .build());
    }
}
