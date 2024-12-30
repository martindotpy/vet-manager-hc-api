package com.vet.hc.api.client.phone.adapter.out.persistence;

import java.util.Set;
import java.util.stream.Collectors;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.client.core.domain.failure.ClientFailure;
import com.vet.hc.api.client.phone.adapter.out.persistence.repository.ClientPhoneHibernateRepository;
import com.vet.hc.api.client.phone.application.mapper.ClientPhoneMapper;
import com.vet.hc.api.client.phone.domain.model.ClientPhone;
import com.vet.hc.api.client.phone.domain.repository.ClientPhoneRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Persistence adapter for client phones.
 */
@RequiredArgsConstructor
@PersistenceAdapter
public final class ClientPhonePersistenceAdapter implements ClientPhoneRepository {
    private final ClientPhoneHibernateRepository clientPhoneHibernateRepository;
    private final ClientPhoneMapper clientPhoneMapper;

    @Override
    public Set<ClientPhone> findAllByClientId(Long id) {
        return clientPhoneHibernateRepository.findAllByClientId(id).stream()
                .map(clientPhoneMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Result<ClientPhone, ClientFailure> save(ClientPhone clientPhone) {
        try {
            return Result.success(
                    clientPhoneMapper
                            .toDomain(clientPhoneHibernateRepository.save(clientPhoneMapper.toEntity(clientPhone))));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ClientFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, ClientFailure> deleteById(Long id) {
        try {
            clientPhoneHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ClientFailure.UNEXPECTED);
        }
    }
}
