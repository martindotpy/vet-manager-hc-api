package com.vet.hc.api.user.adapter.out.persistance;

import java.util.Optional;

import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.adapter.out.persistance.repository.UserHibernateRepository;
import com.vet.hc.api.user.application.mapper.UserMapper;
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

    private RepositoryFailureMapper repositoryFailureMapper = RepositoryFailureMapper.INSTANCE;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Inject
    public UserPersistanceAdapter(UserHibernateRepository userHibernateRepository) {
        this.userHibernateRepository = userHibernateRepository;
    }

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
}
