package com.vluepixel.vetmanager.api.client.core.adapter.out.persistence;

import com.vluepixel.vetmanager.api.client.core.adapter.out.persistence.repository.ClientSpringRepository;
import com.vluepixel.vetmanager.api.client.core.domain.model.Client;
import com.vluepixel.vetmanager.api.client.core.domain.repository.ClientRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link Client}.
 */
@PersistenceAdapter
public class ClientPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<Client, Long, ClientSpringRepository>
        implements ClientRepository {
    public ClientPersistenceAdapter(ClientSpringRepository repository) {
        super(repository);
    }
}
