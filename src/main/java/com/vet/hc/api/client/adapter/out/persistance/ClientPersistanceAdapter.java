package com.vet.hc.api.client.adapter.out.persistance;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.client.adapter.out.mapper.ClientMapper;
import com.vet.hc.api.client.adapter.out.persistance.repository.ClientHibernateRepository;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for client persistance.
 */
@Slf4j
@NoArgsConstructor
public class ClientPersistanceAdapter implements ClientRepository {
    private ClientHibernateRepository clientHibernateRepository;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Inject
    public ClientPersistanceAdapter(ClientHibernateRepository clientHibernateRepository) {
        this.clientHibernateRepository = clientHibernateRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientHibernateRepository.findAll().stream()
                .map(clientMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientHibernateRepository.findById(id)
                .map(clientMapper::toDomain);
    }

    @Override
    public Result<PaginatedResponse<List<Client>>, RepositoryFailure> match(Criteria criteria) {
        try {
            var response = clientHibernateRepository.match(criteria);

            return Result.success(
                    PaginatedResponse.<List<Client>>builder()
                            .message("Clients found")
                            .content(response.getContent().stream()
                                    .map(clientMapper::toDomain)
                                    .toList())
                            .page(response.getPage())
                            .size(response.getSize())
                            .totalElements(response.getTotalElements())
                            .totalPages(response.getTotalPages())
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Field not found in criteria: {}", e.getMessage());
            return Result.failure(RepositoryFailure.FIELD_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Client save(Client client) {
        return clientMapper.toDomain(clientHibernateRepository.save(clientMapper.toEntity(client)));
    }

    @Override
    public Result<Void, ClientFailure> deleteById(Long id) {
        try {
            clientHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ClientFailure.NOT_FOUND);
        }
    }
}
