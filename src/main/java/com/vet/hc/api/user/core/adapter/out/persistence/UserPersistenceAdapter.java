package com.vet.hc.api.user.core.adapter.out.persistence;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryError;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.shared.domain.repository.RepositoryFailureType;
import com.vet.hc.api.user.core.adapter.out.persistence.repository.UserSpringRepository;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adapter for user repository.
 */
@PersistenceAdapter
@RequiredArgsConstructor
public final class UserPersistenceAdapter implements UserRepository, UserDetailsService {
    private final UserSpringRepository userSpringRepository;
    private final UserMapper userMapper;

    @Override
    public Result<User, UserFailure> save(User user) {
        try {
            return Result.success(userSpringRepository.save(userMapper.toEntity(user)));
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            RepositoryFailure failure = MySQLRepositoryError.from(e.getErrorCode(), e.getErrorMessage());

            if (failure.getType() == RepositoryFailureType.DUPLICATED) {
                if (failure.getField().equals("email")) {
                    return Result.failure(UserFailure.EMAIL_ALREADY_IN_USE);
                }
            }

            return Result.failure(UserFailure.UNEXPECTED);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(UserFailure.UNEXPECTED);
        }
    }

    @Override
    public Optional<? extends User> findByEmail(String email) {
        return userSpringRepository.findByEmail(email);
    }

    @Override
    public boolean adminExists() {
        return userSpringRepository.adminExists();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.toEntity(findByEmail(username).get());
    }
}
