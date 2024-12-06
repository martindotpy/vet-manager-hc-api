package com.vet.hc.api.client.core.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.client.core.adapter.out.persistence.repository.ClientHibernateRepository;
import com.vet.hc.api.client.core.application.mapper.ClientMapper;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.client.core.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for client persistence.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class ClientPersistenceAdapter implements ClientRepository {
    private final ClientHibernateRepository clientHibernateRepository;
    private final ClientMapper clientMapper;

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
    public Result<Paginated<Client>, RepositoryFailure> match(Criteria criteria) {
        try {
            var response = clientHibernateRepository.match(criteria);

            return Result.success(
                    Paginated.<Client>builder()
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
