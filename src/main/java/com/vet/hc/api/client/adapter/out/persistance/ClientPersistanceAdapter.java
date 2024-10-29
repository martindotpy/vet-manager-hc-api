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

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Adapter for client persistance.
 */
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
    public PaginatedResponse<List<Client>> match(Criteria criteria) {
        var response = clientHibernateRepository.match(criteria);

        return PaginatedResponse.<List<Client>>builder()
                .message("Clients found.")
                .content(response.getContent().stream()
                        .map(clientMapper::toDomain)
                        .toList())
                .page(response.getPage())
                .size(response.getSize())
                .totalElements(response.getTotalElements())
                .totalPages(response.getTotalPages())
                .build();
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
