package com.vet.hc.api.client.adapter.out.persistance;

import java.util.List;

import com.vet.hc.api.client.adapter.out.persistance.repository.ClientHibernateRepository;
import com.vet.hc.api.client.domain.failure.ClientFailure;
import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Adapter for client persistance.
 */
@NoArgsConstructor
public class ClientPersistanceAdapter implements ClientRepository {
    private ClientHibernateRepository clientHibernateRepository;

    @Inject
    public ClientPersistanceAdapter(ClientHibernateRepository clientHibernateRepository) {
        this.clientHibernateRepository = clientHibernateRepository;
    }

    @Override
    public List<Client> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Client save(Client client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Result<Void, ClientFailure> delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
