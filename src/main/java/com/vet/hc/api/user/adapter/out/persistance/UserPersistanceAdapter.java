package com.vet.hc.api.user.adapter.out.persistance;

import java.util.Optional;

import com.vet.hc.api.user.adapter.out.mapper.UserMapper;
import com.vet.hc.api.user.adapter.out.persistance.repository.UserHibernateRepository;
import com.vet.hc.api.user.domain.model.User;
import com.vet.hc.api.user.domain.repository.UserRepository;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Adapter for user repository.
 */
@NoArgsConstructor
public class UserPersistanceAdapter implements UserRepository {
    private UserHibernateRepository userHibernateRepository;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Inject
    public UserPersistanceAdapter(UserHibernateRepository userHibernateRepository) {
        this.userHibernateRepository = userHibernateRepository;
    }

    @Override
    public User save(User user) {
        return userMapper.toDomain(userHibernateRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userHibernateRepository.findByEmail(email).map(userMapper::toDomain);
    }
}
