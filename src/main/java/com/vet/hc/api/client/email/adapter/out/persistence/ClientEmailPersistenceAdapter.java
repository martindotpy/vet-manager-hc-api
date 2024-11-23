package com.vet.hc.api.client.email.adapter.out.persistence;

import java.util.Set;
import java.util.stream.Collectors;

import com.vet.hc.api.client.email.adapter.out.persistence.repository.ClientEmailHibernateRepository;
import com.vet.hc.api.client.email.application.mapper.ClientEmailMapper;
import com.vet.hc.api.client.email.domain.model.ClientEmail;
import com.vet.hc.api.client.email.domain.repository.ClientEmailRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Persistence adapter for client emails.
 */
@NoArgsConstructor
public class ClientEmailPersistenceAdapter implements ClientEmailRepository {
    private ClientEmailHibernateRepository clientEmailHibernateRepository;

    private ClientEmailMapper clientEmailMapper = ClientEmailMapper.INSTANCE;

    @Inject
    public ClientEmailPersistenceAdapter(ClientEmailHibernateRepository clientEmailHibernateRepository) {
        this.clientEmailHibernateRepository = clientEmailHibernateRepository;
    }

    @Override
    public Set<ClientEmail> findAllByClientId(Long id) {
        return clientEmailHibernateRepository.findAllByClientId(id).stream()
                .map(clientEmailMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Result<ClientEmail, RepositoryFailure> save(ClientEmail clientEmail) {
        try {
            return Result.success(
                    clientEmailMapper
                            .toDomain(clientEmailHibernateRepository.save(clientEmailMapper.toEntity(clientEmail))));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            clientEmailHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }
}