package com.vet.hc.api.user.core.adapter.out.persistence;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.adapter.out.persistence.repository.UserHibernateRepository;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adapter for user repository.
 */
@PersistenceAdapter
@RequiredArgsConstructor
public final class UserPersistenceAdapter implements UserRepository, UserDetailsService {
    private final UserHibernateRepository userHibernateRepository;
    private final RepositoryFailureMapper repositoryFailureMapper;
    private final UserMapper userMapper;

    @Override
    public Result<User, RepositoryFailure> save(User user) {
        try {
            return Result.success(userMapper.toDomain(userHibernateRepository.save(userMapper.toEntity(user))));
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            return Result.failure(
                    repositoryFailureMapper.toRespositoryFailure(MySQLRepositoryFailure.from(e.getErrorCode())));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userHibernateRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public boolean adminExists() {
        return userHibernateRepository.adminExists();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.toEntity(findByEmail(username).get());
    }
}
