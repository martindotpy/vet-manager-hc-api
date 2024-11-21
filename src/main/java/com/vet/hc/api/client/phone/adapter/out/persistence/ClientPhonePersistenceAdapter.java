package com.vet.hc.api.client.phone.adapter.out.persistence;

import java.util.Set;
import java.util.stream.Collectors;

import com.vet.hc.api.client.phone.adapter.out.persistence.repository.ClientPhoneHibernateRepository;
import com.vet.hc.api.client.phone.application.mapper.ClientPhoneMapper;
import com.vet.hc.api.client.phone.domain.model.ClientPhone;
import com.vet.hc.api.client.phone.domain.repository.ClientPhoneRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Persistence adapter for client phones.
 */
@NoArgsConstructor
public class ClientPhonePersistenceAdapter implements ClientPhoneRepository {
    private ClientPhoneHibernateRepository clientPhoneHibernateRepository;

    private ClientPhoneMapper clientPhoneMapper = ClientPhoneMapper.INSTANCE;

    @Inject
    public ClientPhonePersistenceAdapter(ClientPhoneHibernateRepository clientPhoneHibernateRepository) {
        this.clientPhoneHibernateRepository = clientPhoneHibernateRepository;
    }

    @Override
    public Set<ClientPhone> findAllByClientId(Long id) {
        return clientPhoneHibernateRepository.findAllByClientId(id).stream()
                .map(clientPhoneMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Result<ClientPhone, RepositoryFailure> save(ClientPhone clientPhone) {
        try {
            return Result.success(
                    clientPhoneMapper
                            .toDomain(clientPhoneHibernateRepository.save(clientPhoneMapper.toEntity(clientPhone))));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            clientPhoneHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }
}
